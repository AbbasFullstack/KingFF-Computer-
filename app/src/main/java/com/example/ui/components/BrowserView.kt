package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BrowserView(
    browserUrl: String = "github.com",
    activeDoc: String = "CONTRIBUTING.md",
    cursorAction: String = "Reading Docs",
    modifier: Modifier = Modifier
) {
    var showLockedNotice by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Floating AI cursor animation
    val infiniteTransition = rememberInfiniteTransition(label = "ai_cursor")
    val cursorAnimX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_x"
    )
    val cursorAnimY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_y"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFD0D7DE), RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                // Show AI only reminder
                showLockedNotice = true
                scope.launch {
                    delay(2000)
                    showLockedNotice = false
                }
            }
            .testTag("browser_view_container")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Browser Chrome Window Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(Color(0xFFF6F8FA))
                    .border(width = 0.5.dp, color = Color(0xFFD0D7DE))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Window dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFFF5F56)))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFFFBD2E)))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF27C93F)))
                }

                // Centered URL Bar (Locked - AI only)
                Row(
                    modifier = Modifier
                        .height(28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .border(1.dp, Color(0xFFD0D7DE), RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Secure Connection",
                        tint = Color(0xFF57606A),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = browserUrl,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF24292F)
                    )
                }

                // Right Search icon
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(0.5.dp, Color(0xFFD0D7DE), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF57606A),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            // GitHub Top Navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(0xFF0D1117))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // GitHub Logo representation
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = "GitHub",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "GitHub",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                // Search input simulation
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF010409))
                        .border(1.dp, Color(0xFF30363D), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color(0xFF7D8590),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Search or jump to...",
                            color = Color(0xFF7D8590),
                            fontSize = 11.sp
                        )
                    }
                }

                Text(
                    text = "Pull requests",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // GitHub Repo Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF6F8FA))
                    .border(width = 0.5.dp, color = Color(0xFFD0D7DE))
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1F2328)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("S", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "storybookjs",
                        color = Color(0xFF0969DA),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text("/", color = Color(0xFF656D76), fontSize = 14.sp)
                    Text(
                        text = "storybook",
                        color = Color(0xFF0969DA),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Color(0xFFD0D7DE), RoundedCornerShape(10.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("Public", color = Color(0xFF656D76), fontSize = 10.sp, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Next generation UI development and component testing framework",
                    color = Color(0xFF656D76),
                    fontSize = 11.sp
                )
            }

            // Document and Code Content (Scrollable)
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(14.dp)
            ) {
                // File breadcrumb
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = Color(0xFF656D76),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "storybook / $activeDoc",
                        color = Color(0xFF0969DA),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFDDFFF1))
                            .border(1.dp, Color(0xFFB6E3CC), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("Live Preview", color = Color(0xFF1A7F37), fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Text(
                    text = "Contributors Guide",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2328),
                    letterSpacing = (-0.4).sp
                )

                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(3.dp)
                        .width(42.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF0969DA))
                )

                Text(
                    text = "Thanks for your interest in contributing to Storybook! We welcome contributions from everyone. KingFF AI computer use agent is currently scanning setup instructions and running automated checks.",
                    color = Color(0xFF24292F),
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Development Setup",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1F2328)
                )
                Spacer(modifier = Modifier.height(6.dp))

                // Terminal Code block in browser
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF6F8FA))
                        .border(1.dp, Color(0xFFD0D7DE), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text(text = "# Clone and install", color = Color(0xFF656D76), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    Text(text = "git clone https://github.com/storybookjs/storybook.git", color = Color(0xFF24292F), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    Text(text = "cd storybook && yarn install", color = Color(0xFF24292F), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "# Start development server", color = Color(0xFF656D76), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    Text(text = "yarn storybook --port 6006", color = Color(0xFF0969DA), fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // KingFF AI Special Note box
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFDDF4FF))
                        .border(1.dp, Color(0xFF54AEFF).copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF0969DA),
                        modifier = Modifier.size(16.dp).padding(top = 1.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "KingFF Autonomous Execution",
                            color = Color(0xFF0969DA),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Browser & Terminal are locked to AI execution. KingFF reads the requirements, clones the repo, and runs playwright automation autonomously.",
                            color = Color(0xFF24292F),
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Contributor count with pulsing indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1F883D))
                    )
                    Text(
                        text = "2,341 contributors • Updated 3 hours ago",
                        color = Color(0xFF656D76),
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Animated AI Cursor Indicator on the screen
        Box(
            modifier = Modifier
                .offset { IntOffset((220 + cursorAnimX).toInt(), (190 + cursorAnimY).toInt()) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A).copy(alpha = 0.95f))
                    .border(1.dp, Color(0xFF38BDF8), RoundedCornerShape(12.dp))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.NearMe,
                    contentDescription = "AI Cursor",
                    tint = Color(0xFF38BDF8),
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "KingFF: $cursorAction",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Top-right AI-only floating badge
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 52.dp, end = 12.dp)
        ) {
            AiOnlyLockBadge(activeAction = "Browsing")
        }

        // Notice when user taps inside the AI-only area
        if (showLockedNotice) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .shadow(12.dp, RoundedCornerShape(14.dp))
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF111827).copy(alpha = 0.96f))
                    .border(1.dp, Color(0xFFF59E0B), RoundedCornerShape(14.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFFF59E0B),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "AI Only Access (Read-Only)",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "KingFF operates the browser. Use chat below to give instructions.",
                            color = Color(0xFFD1D5DB),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
