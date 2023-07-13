/*
 * Copyright (C) 2022-2023 Tony Guyot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.tonyguyot.flagorama.data.utils

import android.util.Log
import retrofit2.Response
import java.lang.Exception

const val TAG = "flagorama-http"

/**
 * Base data source class for all network access.
 */
abstract class BaseRemoteDataSource {
    protected suspend fun <T, R> fetchResource(
        call: suspend () -> Response<R>,
        transform: suspend (R) -> T
    ): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    return Resource.success(transform(body))
                Log.e(TAG, "HTTP OK but no body in response")
                return Resource.error(Exception("empty body"))
            }
            Log.e(TAG, "HTTP error ${response.code()}")
            return Resource.error(Exception("HTTP error ${response.code()}"))
        } catch (e: Exception) {
            Log.e(TAG, "Other exception during processing of HTTP response", e)
            return Resource.error(e)
        }
    }
}