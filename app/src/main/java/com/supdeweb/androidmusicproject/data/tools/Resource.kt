package com.supdeweb.androidmusicproject.data.tools

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(var status: Status, val data: T?, val message: String?) {
    fun <R> mapData(
        transform: (T?) -> R,
    ): Resource<R> {
        return when (this.status) {
            Status.SUCCESS -> success(transform(data))
            Status.ERROR -> error(this.message ?: "", null)
            Status.LOADING -> loading(transform(data))
        }
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> error(e: Exception, data: T?): Resource<T> {
            val msg =
                if (e.message == null) "${e::class.java.canonicalName} : ${e.message}"
                else e.message

            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }
}
