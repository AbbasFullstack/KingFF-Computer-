package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopHeader(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF0A0A0A))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left: Close button
        IconButton(
            onClick = onCloseClick,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.08f))
                .testTag("header_close_button")
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close session",
                tint = Color.White.copy(alpha = 0.75f),
                modifier = Modifier.size(18.dp)
            )
        }

        // Center: Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "KingFF's computer",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                letterSpacing = (-0.3).sp
            )
        }

        // Right: Monitor screen dropdown
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
                    .clickable { isMenuExpanded = !isMenuExpanded }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .testTag("header_monitor_dropdown")
            ) {
                Icon(
                    imageVector = Icons.Default.DesktopWindows,
                    contentDescription = "Screen controls",
                    tint = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Open screen menu",
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp)
                )
            }

            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
                modifier = Modifier
                    .background(Color(0xFF1E1E22))
                    .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                    .padding(4.dp)
            ) {
                DropdownMenuItem(
                    text = {
                        Column {
                            Text("Display: Fullscreen", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Text("1080p • 60 FPS Stream", color = Color.Gray, fontSize = 11.sp)
                        }
                    },
                    leadingIcon = {
                        Icon(Icons.Default.DesktopWindows, contentDescription = null, tint = Color(0xFF60A5FA), modifier = Modifier.size(18.dp))
                    },
                    onClick = { isMenuExpanded = false }
                )
                DropdownMenuItem(
                    text = {
                        Column {
                            Text("Monitor Mode: Live", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Text("Ultra-low latency pipe", color = Color(0xFF22C55E), fontSize = 11.sp)
                        }
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Speed, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(18.dp))
                    },
                    onClick = { isMenuExpanded = false }
                )
                DropdownMenuItem(
                    text = {
                        Column {
                            Text("AI Only Access", color = Color(0xFFFBBF24), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("Spectator view (read-only)", color = Color.Gray, fontSize = 11.sp)
                        }
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(18.dp))
                    },
                    onClick = { isMenuExpanded = false }
                )
            }
        }
    }
}
