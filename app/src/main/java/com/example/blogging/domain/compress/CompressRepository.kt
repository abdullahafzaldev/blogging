package com.example.blogging.domain.compress

import android.net.Uri

interface CompressRepository {
    suspend fun compressImage(uri: Uri): Uri
}