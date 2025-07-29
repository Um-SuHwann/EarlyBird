package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun TodoInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "What would you like to do?",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(
                color = EarlyBirdTheme.colors.white,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFE9E9E9),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = EarlyBirdTheme.colors.fontBlack
            ),
            modifier = Modifier.fillMaxWidth()
        ) { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFC9C9C9)
                )
            }
            innerTextField()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoInputFieldPreview() {
    TodoInputField(
        value = "",
        onValueChange = {},
        placeholder = "Add your task here"
    )
}