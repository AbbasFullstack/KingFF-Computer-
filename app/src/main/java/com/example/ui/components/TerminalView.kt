package com.example.ui.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TerminalView(
    activeCommand: String = "pip install playwright",
    extraLogs: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    var showLockedNotice by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Blinking cursor
    val infiniteTransition = rememberInfiniteTransition(label = "cursor_blink")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_opacity"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0D0D0D))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                showLockedNotice = true
                scope.launch {
                    delay(2000)
                    showLockedNotice = false
                }
            }
            .testTag("terminal_view_container")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Terminal Window Titlebar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(Color(0xFF1A1A1A))
                    .border(width = 0.5.dp, color = Color.White.copy(alpha = 0.08f))
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
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

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "kingff@ollama — zsh — 80×24",
                    color = Color.White.copy(alpha = 0.6f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp
                )
            }

            // Terminal Console Scroll Area
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(14.dp)
            ) {
                Text(
                    text = "Last login: Sat Oct 31 08:34:12 on ttys003",
                    color = Color.White.copy(alpha = 0.4f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Prompt 1: ollama list
                TerminalPromptLine("ollama list")
                Text(
                    text = "NAME              SIZE    MODIFIED",
                    color = Color.White.copy(alpha = 0.6f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "llama3.2:latest   2.0GB   2 hours ago",
                    color = Color.White.copy(alpha = 0.95f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "mistral:latest     4.1GB   1 day ago",
                    color = Color.White.copy(alpha = 0.95f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Prompt 2: initialization script
                TerminalPromptLine("./run.sh --browser --terminal")
                Text(
                    text = "[KingFF] Starting offline agent session...",
                    color = Color(0xFFF8E71C),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "✓ Browser session attached: github.com",
                    color = Color.White.copy(alpha = 0.8f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "✓ Terminal socket linked to local runner",
                    color = Color.White.copy(alpha = 0.8f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "● Agent ready — streaming live computer view",
                    color = Color(0xFF7ED321),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Prompt 3: pip install playwright
                TerminalPromptLine(activeCommand)
                Text(
                    text = "Collecting playwright\n  Downloading playwright-1.48.0-py3-none-manylinux.whl (40.2 MB)\n  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 40.2/40.2 MB 38.4 MB/s\nInstalling collected packages: greenlet, pyee, playwright\nSuccessfully installed playwright-1.48.0",
                    color = Color(0xFF38BDF8),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "[KingFF] playwright install chromium --with-deps [DONE]",
                    color = Color(0xFF22C55E),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

                // Extra dynamic logs if provided
                extraLogs.forEach { log ->
                    Text(
                        text = log,
                        color = Color(0xFFE2E8F0),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Active prompt with blinking cursor
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color(0xFF7ED321), fontWeight = FontWeight.Bold)) {
                                append("➜ ")
                            }
                            withStyle(SpanStyle(color = Color(0xFF4A90E2))) {
                                append("~/kingff-ai ")
                            }
                        },
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp
                    )
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height(14.dp)
                            .alpha(cursorAlpha)
                            .background(Color(0xFF22C55E))
                    )
                }
            }
        }

        // Top right AI-only lock pill
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 52.dp, end = 12.dp)
        ) {
            AiOnlyLockBadge(activeAction = "Executing CLI")
        }

        // User touch notification
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
                            text = "Terminal is AI-Controlled Only",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "KingFF executes commands autonomously in real-time.",
                            color = Color(0xFFD1D5DB),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TerminalPromptLine(command: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color(0xFF7ED321), fontWeight = FontWeight.Bold)) {
                    append("➜ ")
                }
                withStyle(SpanStyle(color = Color(0xFF4A90E2))) {
                    append("~/kingff-ai ")
                }
                withStyle(SpanStyle(color = Color.White)) {
                    append(command)
                }
            },
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp
        )
    }
}
