package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

enum class Mood {
    BAD, NORMAL, GOOD
}

@Composable
fun MoodSelector(
    selectedMood: Mood?,
    onMoodSelected: (Mood) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoodButton(
            imageRes = R.drawable.reservation_mood_bad,
            selectedImageRes = R.drawable.reservation_mood_bad_selected,
            text = "Bad",
            isSelected = selectedMood == Mood.BAD,
            selectedColor = Color(0xFFF44336), // 빨간색
            onClick = { onMoodSelected(Mood.BAD) }
        )

        MoodButton(
            imageRes = R.drawable.reservation_mood_normal,
            selectedImageRes = R.drawable.reservation_mood_normal_selected,
            text = "Normal",
            isSelected = selectedMood == Mood.NORMAL,
            selectedColor = Color(0xFFFF8B48), // 주황색
            onClick = { onMoodSelected(Mood.NORMAL) }
        )

        MoodButton(
            imageRes = R.drawable.reservation_mood_good,
            selectedImageRes = R.drawable.reservation_mood_good_selected,
            text = "Good",
            isSelected = selectedMood == Mood.GOOD,
            selectedColor = Color(0xFF0076C6), // 청록색
            onClick = { onMoodSelected(Mood.GOOD) }
        )
    }
}
@Composable
fun MoodButton(
    imageRes: Int,
    selectedImageRes: Int,
    text: String,
    isSelected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected)
        selectedColor.copy(alpha = 0.1f)
    else
        EarlyBirdTheme.colors.white
    val borderColor = if (isSelected)
        selectedColor
    else
        Color(0xFFF0F0F0)

    val fontColor = if (isSelected)
        selectedColor
    else
        Color(0xFFC9C9C9)

    Column(
        modifier = modifier
            .width(99.dp)
            .height(166.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = if(isSelected) selectedImageRes else imageRes),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
        )

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
            color = fontColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoodSelectorPreview() {
    MoodSelector(
        selectedMood = Mood.BAD,
        onMoodSelected = {}
    )
}