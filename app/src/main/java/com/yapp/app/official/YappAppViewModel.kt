package com.yapp.app.official

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.dataapi.OperationsRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YappAppViewModel @Inject constructor(
    private val operationsRepository: OperationsRepository,
) : ViewModel() {

    fun onCommonErrorDialogRecommendActionButtonClick(
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit,
    ) = viewModelScope.launch {
        runCatchingIgnoreCancelled {
            operationsRepository.getUsageInquiryLink()
        }.onSuccess(onSuccess)
            .onFailure(onError)
    }
}