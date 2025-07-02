package com.earlybird.earlybirdcompose.data.model

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlarmInfo(
    val todo: String,
    val hour: Int,
    val minute: Int,
    val amPm: String, // "AM" 또는 "PM"
    val isRepeating: Boolean,
    val isVibrationEnabled: Boolean,
    val focusDurationMinutes: Int
) : Parcelable
