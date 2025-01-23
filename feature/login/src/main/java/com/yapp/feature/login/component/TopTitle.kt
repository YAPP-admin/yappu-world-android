package com.yapp.feature.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.login.R

@Composable
fun TopTitle(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 72.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(bottom = 56.dp),
            text = stringResource(R.string.login_title),
            style = YappTheme.typography.title2Bold,
        )
        Text(
            text = stringResource(R.string.login_title_email),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 4.dp),
            style = YappTheme.typography.label1NormalMedium,
            color = YappTheme.colorScheme.labelAlternative
        )
        Image(
            painter = painterResource(id = R.drawable.illust_login_yappu),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp)
        )
    }
}