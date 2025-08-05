package com.earlybird.earlybirdcompose.presentation.screen.timer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

data class MoodOption(
    val emoji: String,
    val label: String,
    val value: Int
)

enum class MoodCategory(val label: String, val value: Int) {
    BAD("Bad", 1),
    NORMAL("Normal", 2), 
    GOOD("Good", 3)
}

data class DetailedMood(
    val label: String,
    val value: Int,
    val category: MoodCategory
)

@Composable
fun MoodModal(
    isVisible: Boolean,
    onMoodSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            MoodModalContent(
                onMoodSelected = { mood ->
                    onMoodSelected(mood)
                    onDismiss()
                },
                onCancel = onDismiss,
                modifier = Modifier.clickable { /* 클릭 이벤트 막기 */ }
            )
        }
    }
}

@Composable
private fun MoodModalContent(
    onMoodSelected: (Int) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf<MoodCategory?>(null) }
    var selectedDetailedMood by remember { mutableStateOf<DetailedMood?>(null) }
    
    // 1단계: 기본 기분 카테고리
    val moodCategories = listOf(
        MoodCategory.BAD,
        MoodCategory.NORMAL,
        MoodCategory.GOOD
    )
    
    // 2단계: 세부 기분들
    val detailedMoods = listOf(
        // Bad moods
        DetailedMood("Anxious", 1, MoodCategory.BAD),
        DetailedMood("Sad", 2, MoodCategory.BAD),
        DetailedMood("Unmotivated", 3, MoodCategory.BAD),
        
        // Normal moods  
        DetailedMood("Okay", 4, MoodCategory.NORMAL),
        DetailedMood("Busy", 5, MoodCategory.NORMAL),
        DetailedMood("Distracted", 6, MoodCategory.NORMAL),
        
        // Good moods
        DetailedMood("Relaxed", 7, MoodCategory.GOOD),
        DetailedMood("Proud", 8, MoodCategory.GOOD),
        DetailedMood("Grateful", 9, MoodCategory.GOOD)
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (selectedCategory == null) "How was your task?" else "More specifically?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = EarlyBirdTheme.colors.fontBlack,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Check your feelings ( 1 to 3 )",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = EarlyBirdTheme.colors.fontBlack,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // 1단계: 기본 카테고리 선택 (항상 표시)
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Bad 카테고리
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(
                            if (selectedCategory == MoodCategory.BAD) 
                                R.drawable.reservation_mood_bad_selected 
                            else 
                                R.drawable.reservation_mood_bad
                        ),
                        contentDescription = "bad_icon",
                        modifier = Modifier
                            .size(52.dp)
                            .clickable { selectedCategory = MoodCategory.BAD }
                    )

                    // Bad 세부 기분들
                    if (selectedCategory == MoodCategory.BAD) {
                        val badMoods = detailedMoods.filter { it.category == MoodCategory.BAD }
                        badMoods.forEach { mood ->
                            Spacer(modifier = Modifier.height(24.dp))
                            DetailedMoodButton(
                                mood = mood,
                                isSelected = selectedDetailedMood == mood,
                                selectedCategory = MoodCategory.BAD,
                                onClick = { selectedDetailedMood = mood }
                            )
                        }
                    }
                }
                
                // Normal 카테고리
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(
                            if (selectedCategory == MoodCategory.NORMAL) 
                                R.drawable.reservation_mood_normal_selected 
                            else 
                                R.drawable.reservation_mood_normal
                        ),
                        contentDescription = "normal_icon",
                        modifier = Modifier
                            .size(52.dp)
                            .clickable { selectedCategory = MoodCategory.NORMAL }
                    )
                    // Normal 세부 기분들
                    if (selectedCategory == MoodCategory.NORMAL) {
                        val normalMoods = detailedMoods.filter { it.category == MoodCategory.NORMAL }
                        normalMoods.forEach { mood ->
                            Spacer(modifier = Modifier.height(24.dp))
                            DetailedMoodButton(
                                mood = mood,
                                isSelected = selectedDetailedMood == mood,
                                selectedCategory = MoodCategory.NORMAL,
                                onClick = { selectedDetailedMood = mood }
                            )
                        }
                    }
                }
                // Good 카테고리
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(
                            if (selectedCategory == MoodCategory.GOOD) 
                                R.drawable.reservation_mood_good_selected 
                            else 
                                R.drawable.reservation_mood_good
                        ),
                        contentDescription = "good_icon",
                        modifier = Modifier
                            .size(52.dp)
                            .clickable { selectedCategory = MoodCategory.GOOD }
                    )

                    // Good 세부 기분들
                    if (selectedCategory == MoodCategory.GOOD) {
                        val goodMoods = detailedMoods.filter { it.category == MoodCategory.GOOD }
                        goodMoods.forEach { mood ->
                            Spacer(modifier = Modifier.height(24.dp))
                            DetailedMoodButton(
                                mood = mood,
                                isSelected = selectedDetailedMood == mood,
                                selectedCategory = MoodCategory.GOOD,
                                onClick = { selectedDetailedMood = mood }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                if(selectedCategory == null){
                    Button(
                        onClick = onCancel,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6E6E6E),
                            contentColor = EarlyBirdTheme.colors.white
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Skip now",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                else{
                    Spacer(Modifier.height(34.dp))
                }

                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { 
                        selectedDetailedMood?.let { onMoodSelected(it.value) }
                    },
                    enabled = selectedDetailedMood != null,
                    modifier = Modifier
                        .height(42.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EarlyBirdTheme.colors.black,
                        contentColor = EarlyBirdTheme.colors.white,
                        disabledContainerColor = Color(0xFFD5D5D5),
                        disabledContentColor = EarlyBirdTheme.colors.white
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Done",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailedMoodButton(
    mood: DetailedMood,
    isSelected: Boolean,
    selectedCategory: MoodCategory,
    onClick: () -> Unit
) {
    // MoodCategory에 따른 색상 설정
    val selectedColor = when (selectedCategory) {
        MoodCategory.BAD -> Color(0xFFF45136)      // 빨간색
        MoodCategory.NORMAL -> Color(0xFFFB8C00)   // 주황색
        MoodCategory.GOOD -> Color(0xFF0076C6)     // 녹색
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = if (isSelected) selectedColor.copy(alpha = 0.1f) else Color.Transparent,
        border = if (isSelected) 
            androidx.compose.foundation.BorderStroke(2.dp, selectedColor)
        else 
            androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFB7B7B7))
    ) {
        Box(
            modifier = Modifier.padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = mood.label,
                fontSize = 12.sp,
                fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) selectedColor else EarlyBirdTheme.colors.fontBlack
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MoodModalPreview() {
    EarlyBirdComposeTheme {
        MoodModal(
            isVisible = true,
            onMoodSelected = { },
            onDismiss = { }
        )
    }
}