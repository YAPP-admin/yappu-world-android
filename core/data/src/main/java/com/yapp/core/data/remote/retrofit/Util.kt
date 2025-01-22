package com.yapp.core.data.remote.retrofit

import com.yapp.model.exceptions.ForbiddenException
import com.yapp.model.exceptions.NetworkException
import com.yapp.model.exceptions.NotFoundException
import com.yapp.model.exceptions.RequestFailException
import com.yapp.model.exceptions.UnknownException

internal fun handleCommonError(httpStatusCode: Int) = when (httpStatusCode) {
    400 -> RequestFailException()
    403 -> ForbiddenException()
    404 -> NotFoundException()
    500, 501, 502, 503, 504, 505 -> NetworkException()
    else -> UnknownException()
}