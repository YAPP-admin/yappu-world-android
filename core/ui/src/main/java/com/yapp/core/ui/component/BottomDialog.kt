package com.yapp.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        modifier = Modifier
            .imePadding()
            .padding(8.dp),
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        dragHandle = null,
        onDismissRequest = { onDismissRequest() },
        shape = RoundedCornerShape(0.dp),
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = YappTheme.colorScheme.backgroundElevatedNormal,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomDialogPreview() {
    YappTheme {
        BottomDialog(onDismissRequest = { }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Preview")
            }
        }
    }
}