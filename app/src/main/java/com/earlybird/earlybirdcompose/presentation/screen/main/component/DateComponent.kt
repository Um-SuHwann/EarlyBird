package com.earlybird.earlybirdcompose.presentation.screen.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.presentation.screen.main.component.BirdImageComponent
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DateComponent(
    modifier: Modifier = Modifier,
    dayStreak: Int = 1
) {
    Surface(
        modifier = modifier
            .widthIn(min = 127.dp)
            .heightIn(min = 72.dp),
        color = EarlyBirdTheme.colors.white,
        shape = RoundedCornerShape(50.dp),
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = getTodayDateFormatted(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = EarlyBirdTheme.colors.fontBlack
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.main_day_streak),
                    contentDescription = "day streak icon",
                    modifier = Modifier.width(16.dp).height(25.dp)
                )
                Text(
                    text = "${dayStreak}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0075D4)
                )
            }
        }
        }
    }
}

fun getTodayDateFormatted(): String {
    val today = LocalDate.now()
    val dayOfMonth = today.dayOfMonth
    val ordinalSuffix = when {
        dayOfMonth in 11..13 -> "th"
        dayOfMonth % 10 == 1 -> "st"
        dayOfMonth % 10 == 2 -> "nd"
        dayOfMonth % 10 == 3 -> "rd"
        else -> "th"
    }
    val formatter = DateTimeFormatter.ofPattern("MMMM d'$ordinalSuffix'", Locale.ENGLISH)
    return today.format(formatter)
}

@Preview(showBackground = true)
@Composable
fun DateComponentPreview() {
    DateComponent(dayStreak = 7)
}