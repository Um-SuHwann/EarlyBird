package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(
    onBackClick: () -> Unit = {}
) {
    Column {
        TopAppBar(
            title = { Text("") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Image(
                        painter = painterResource(R.drawable.reservation_back_icon),
                        contentDescription = "뒤로가기",
                        modifier = Modifier.width(21.dp), // 크기 조절 가능
                        contentScale = ContentScale.Fit
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = EarlyBirdTheme.colors.white // 배경색 설정
            ),
        )
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFD7D7D7))
    }

}

@Preview(showBackground = true)
@Composable
fun BackTopBarPreview(){
    EarlyBirdComposeTheme {
        BackTopBar() { }
    }
}