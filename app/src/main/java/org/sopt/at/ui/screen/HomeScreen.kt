package org.sopt.at.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.R

data class Banner(val imageRes: Int)
data class Content(val imageRes: Int, val title: String, val badge: String? = null)
data class ContentSection(val title: String, val contents: List<Content>, val showMore: Boolean = true)
data class Partner(val imageRes: Int, val name: String)

@Composable
fun HomeScreen(
    onProfileClicked: () -> Unit
) {
    val mainBanners = listOf(
        Banner(R.drawable.img_main_poster),
        Banner(R.drawable.img_main_poster),
        Banner(R.drawable.img_main_poster),
        Banner(R.drawable.img_main_poster),
        Banner(R.drawable.img_main_poster),
    )

    val partners = listOf(
        Partner(R.drawable.img_home_genre_kbo, "KBO"),
        Partner(R.drawable.img_home_genre_appletv, "AppleTV+"),
        Partner(R.drawable.img_home_genre_kbl, "KBL"),
        Partner(R.drawable.img_home_genre_kids, "KIDS"),
        Partner(R.drawable.img_home_genre_ufc, "UFC")

    )

    val contentSections = listOf(
        ContentSection(
            title = "오늘의 티빙 TOP 20",
            contents = listOf(
                Content(R.drawable.img_home_drama_banner1, "그놈은 흑염룡", "NEW"),
                Content(R.drawable.img_home_entertainment_banner1, "아는형님", "NEW"),
                Content(R.drawable.img_home_animation_banner1, "꿈빛 파티시엘"),
                Content(R.drawable.img_home_animation_banner2, "우리들의 공룡일기"),
                Content(R.drawable.img_home_animation_banner3, "짱구는 못 말려"),
                Content(R.drawable.img_home_movie_banner1, "화사한 그녀"),
                Content(R.drawable.img_home_movie_banner2, "대가족")
            )
        ),
        ContentSection(
            title = "지금 방영 중인 콘텐츠",
            contents = listOf(
                Content(R.drawable.img_home_drama_banner1, "그놈은 흑염룡", "NEW"),
                Content(R.drawable.img_home_entertainment_banner2, "아는형님", "NEW"),
                Content(R.drawable.img_home_movie_banner2, "대가족"),
                Content(R.drawable.img_home_movie_banner3, "플레인"),
                Content(R.drawable.img_home_entertainment_banner3, "런닝맨"),
                Content(R.drawable.img_home_movie_banner1, "화사한 그녀")
            )
        ),
        ContentSection(
            title = "애니메이션 추천",
            contents = listOf(
                Content(R.drawable.img_home_animation_banner1, "꿈빛 파티시엘"),
                Content(R.drawable.img_home_animation_banner2, "우리들의 공룡일기"),
                Content(R.drawable.img_home_animation_banner3, "짱구는 못 말려"),
                Content(R.drawable.img_home_animation_banner1, "꿈빛 파티시엘"),
                Content(R.drawable.img_home_animation_banner2, "우리들의 공룡일기"),
                Content(R.drawable.img_home_animation_banner3, "짱구는 못 말려")
            )
        ),
        ContentSection(
            title = "추천 영화",
            contents = listOf(
                Content(R.drawable.img_home_movie_banner1, "화사한 그녀"),
                Content(R.drawable.img_home_movie_banner2, "대가족"),
                Content(R.drawable.img_home_movie_banner3, "플레인"),
                Content(R.drawable.img_home_movie_banner1, "화사한 그녀"),
                Content(R.drawable.img_home_movie_banner2, "대가족"),
                Content(R.drawable.img_home_movie_banner3, "플레인")
            )
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Logo",
                        tint = Color.Red
                    )

                    Row(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home_broadcast),
                            contentDescription = "Cast",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.img_my_profile),
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .clickable { onProfileClicked() }
                        )
                    }
                }

            }
            item {
                CategoryRow()
            }

            item {
                MainBannerSection(banners = mainBanners)
            }

            item {
                PartnerSection(partners = partners)
            }

            contentSections.forEach { section ->
                item {
                    ContentSectionComponent(
                        title = section.title,
                        contents = section.contents,
                        showMore = section.showMore
                    )
                }
            }
        }
    }
}

@Composable
fun PartnerSection(partners: List<Partner>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(partners) { partner ->
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
                    .background(Color(0xFF1A1A1A), RoundedCornerShape(4.dp))
                    .border(1.dp, Color(0xFF333333), RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = partner.imageRes),
                    contentDescription = partner.name,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight(0.7f),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun CategoryRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val categories = listOf("DRAMA", "VARIETY", "MOVIE", "SPORTS", "ANIMATION")

        categories.forEach { category ->
            Text(
                text = category,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.clickable { }
            )
        }
    }
}

@Composable
fun MainBannerSection(banners: List<Banner>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(banners) { banner ->
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(id = banner.imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


@Composable
fun ContentSectionComponent(
    title: String,
    contents: List<Content>,
    showMore: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if (showMore) {
                Text(
                    text = "더보기",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { }
                )
            }
        }

        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(contents) { content ->
                ContentCard(content = content)
            }
        }
    }
}

@Composable
fun ContentCard(content: Content) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(4.dp))
        ) {
            Image(
                painter = painterResource(id = content.imageRes),
                contentDescription = content.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        content.badge?.let { badge ->
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .background(
                        color = when (badge) {
                            "NEW" -> Color.Red
                            "ORIGINAL" -> Color(0xFFFF6D00)
                            else -> Color.Gray
                        },
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = badge,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (content.imageRes == R.drawable.img_home_drama_banner1 ||
            content.imageRes == R.drawable.img_home_entertainment_banner1) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "ONLY",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (content.title.contains("TOP") || content.title.contains("드라마 1")) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomStart)
                    .padding(start = 8.dp, bottom = 8.dp)
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "1",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
