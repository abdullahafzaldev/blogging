package com.example.blogging.data.remote.auth

import com.example.blogging.models.users.AuthUser
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await

class AuthDataStoreImpl  : AuthDataSource{

    companion object {
        private const val NAME_REF_USERS = "users"
        private const val NAME_REF_TOKENS = "tokens"
        private const val FIELD_TIME_UPDATE = "timeUpdate"

        private const val FIELD_TIME_CREATE = "timeCreate"
        private const val FIELD_ARRAY_TOKENS = "tokens"
    }

    private val auth = Firebase.auth
    private val nodeUsers = Firebase.firestore.collection(NAME_REF_USERS)
    private val nodeTokens = Firebase.firestore.collection(NAME_REF_TOKENS)
    private val database = Firebase.firestore


    override suspend fun authWithCredential(credential: AuthCredential): AuthUser {
        val resultAuth = auth.signInWithCredential(credential).await()
        val user = resultAuth.user!!
        val refUser = nodeUsers.document(user.uid)
        val docUser = refUser.get().await()
        return if (docUser.exists()) {
            addingTokenUser(uuidUser = user.uid)
            docUser.toMyUser()
        } else {
            AuthUser(id = user.uid)
        }
    }

    private fun DocumentSnapshot.toMyUser(): AuthUser {
        return this.toObject<AuthUser>()!!.copy(
            id = id,
        )
    }

    override suspend fun addingTokenUser(newToken: String?, uuidUser: String?, oldToken: String) {
        val finishToken = newToken ?: Firebase.messaging.token.await()

        (uuidUser ?: auth.currentUser?.uid)?.let { idDocument ->
            val refToken = nodeTokens.document(idDocument)

            database.runTransaction { transaction ->
                val currentToken = transaction.get(refToken)
                if (currentToken.exists()) {

                    (currentToken.get(FIELD_ARRAY_TOKENS) as? HashMap<*, *>)?.let {
                        if (!it.containsKey(finishToken)) {
                            transaction.update(
                                /* documentRef = */
                                refToken,
                                /* field = */
                                "$FIELD_ARRAY_TOKENS.$finishToken",
                                /* value = */
                                mapOf(FIELD_TIME_CREATE to FieldValue.serverTimestamp()),
                            )
                        }

                        if (it.containsKey(oldToken)) {
                            transaction.update(
                                /* documentRef = */
                                refToken,
                                /* field = */
                                "$FIELD_ARRAY_TOKENS.$oldToken",
                                /* value = */
                                FieldValue.delete(),
                            )
                        }
                    }

                } else {
                    transaction.set(
                        /* documentRef = */
                        refToken,
                        /* data = */
                        hashMapOf(
                            FIELD_ARRAY_TOKENS to mapOf(
                                finishToken to mapOf(FIELD_TIME_CREATE to FieldValue.serverTimestamp())
                            )
                        ),
                    )
                }
            }.await()
        }
    }
}