package com.yapp.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall

enum class UserRole(val role: String) {
    ADMIN("관리자"),
    ALUMNI("정회원"),
    GRADUATE("수료회원"),
    ACTIVE("활동회원"),
    STAFF("운영진");

    companion object {
        fun fromRole(role: String): UserRole {
            return entries.find { it.role == role } ?: ADMIN
        }
    }
}


@Composable
fun RoleChip(
    modifier: Modifier = Modifier,
    role: UserRole,
) {
    when (role) {
        UserRole.ADMIN -> YappChipSmall(
            text = UserRole.ADMIN.role,
            colorType = ChipColorType.Gray,
            isFill = false
        )

        UserRole.ALUMNI -> YappChipSmall(
            text = UserRole.ALUMNI.role,
            colorType = ChipColorType.Sub,
            isFill = false
        )

        UserRole.GRADUATE -> YappChipSmall(
            text = UserRole.GRADUATE.role,
            colorType = ChipColorType.Main,
            isFill = false
        )

        UserRole.ACTIVE -> YappChipSmall(
            text = UserRole.ACTIVE.role,
            colorType = ChipColorType.Main,
            isFill = false
        )

        UserRole.STAFF -> YappChipSmall(
            text = UserRole.STAFF.role,
            colorType = ChipColorType.Gray,
            isFill = false
        )
    }
}

