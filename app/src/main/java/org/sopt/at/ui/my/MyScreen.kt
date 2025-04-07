package org.sopt.at.ui.my

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.ui.theme.TvingRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(
    viewModel: MyViewModel,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val logoutEvent by viewModel.logoutEvent.collectAsState()

    LaunchedEffect(logoutEvent) {
        if (logoutEvent) {
            onLogoutClick()
            viewModel.resetLogoutEvent()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "알림",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "설정",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.Black),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(label = "HOME", isSelected = false)
                BottomNavItem(label = "Shorts", isSelected = false, showBadge = true)
                BottomNavItem(label = "LIVE", isSelected = false)
                BottomNavItem(label = "SEARCH", isSelected = false)
                BottomNavItem(label = "HISTORY", isSelected = false)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(TvingRed),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "T",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = uiState.userId,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .background(
                                color = Color.DarkGray.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Text(
                            text = "프로필 변경",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.DarkGray.copy(alpha = 0.3f))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "나의 이용권",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }

                            Text(
                                text = if (uiState.hasSubscription) uiState.subscriptionName else "사용중인 이용권이 없습니다",
                                color = if (uiState.hasSubscription) Color.White else Color.Gray,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "티빙캐시",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }

                            Text(
                                text = uiState.cashAmount.toString(),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Text(
                    text = "이용권을 구매하고 인기 콘텐츠를 더 보기 시리즈와 다양한 영화 콘텐츠를 자유롭게 시청하세요!",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                MenuItemWithArrow(
                    text = "이용권 구독",
                    onClick = {  }
                )

                MenuItemWithArrow(
                    text = "회원정보 수정",
                    onClick = { }
                )

                MenuItemWithArrow(
                    text = "프로모션 정보 수신 동의",
                    onClick = {  }
                )

                MenuItemWithArrow(
                    text = "다운로드",
                    onClick = {  }
                )

                Text(
                    text = "다운로드 콘텐츠는 이 곳에서 시청할 수 있어요",
                    color = Color(0xFF1E88E5),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )

                MenuItemWithArrow(
                    text = "라이브 예약 알림",
                    onClick = {  }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.logout() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "로그아웃",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MenuItemWithArrow(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "더보기",
            tint = Color.Gray
        )
    }

    Divider(color = Color.DarkGray.copy(alpha = 0.5f))
}

@Composable
fun BottomNavItem(
    label: String,
    isSelected: Boolean,
    showBadge: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(if (isSelected) TvingRed else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            if (showBadge) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(TvingRed, CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }

        Text(
            text = label,
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}
