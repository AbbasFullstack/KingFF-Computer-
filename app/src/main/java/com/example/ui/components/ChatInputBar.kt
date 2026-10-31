package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TaskSuggestion

@Composable
fun ChatInputBar(
    isThinking: Boolean,
    onSendTask: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var queryText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val suggestions = listOf(
        TaskSuggestion("FaucetPay balance", "Check FaucetPay balance and crypto payouts", com.example.model.ComputerTab.BROWSER, "Navigating to FaucetPay"),
        TaskSuggestion("Search GitHub bot", "Search GitHub for KingFF bot repository", com.example.model.ComputerTab.BROWSER, "Searching GitHub"),
        TaskSuggestion("pip install playwright", "Install playwright and chromium browser binaries", com.example.model.ComputerTab.TERMINAL, "Running pip install"),
        TaskSuggestion("Check GPU & models", "Run ollama list and check system GPU memory", com.example.model.ComputerTab.TERMINAL, "Inspecting Ollama")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0F0F12))
            .border(width = 0.5.dp, color = Color.White.copy(alpha = 0.08f))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Suggested Task Pills
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestions.forEach { suggestion ->
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.07f))
                        .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                        .clickable {
                            queryText = suggestion.prompt
                            onSendTask(suggestion.prompt)
                            queryText = ""
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color(0xFF60A5FA),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = suggestion.title,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Input Field and Blue Arrow Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF1C1C21))
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
                .padding(horizontal = 14.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
            ) {
                if (queryText.isEmpty()) {
                    Text(
                        text = if (isThinking) "KingFF is thinking..." else "Ask KingFF to do something...",
                        color = if (isThinking) Color(0xFF60A5FA) else Color.White.copy(alpha = 0.45f),
                        fontSize = 14.sp
                    )
                }

                BasicTextField(
                    value = queryText,
                    onValueChange = { queryText = it },
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                    cursorBrush = SolidColor(Color(0xFF38BDF8)),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {
                        if (queryText.isNotBlank() && !isThinking) {
                            onSendTask(queryText.trim())
                            queryText = ""
                            focusManager.clearFocus()
                        }
                    }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("chat_input_textfield"),
                    singleLine = true
                )
            }

            // Send Button (Blue circle with arrow)
            val canSend = queryText.isNotBlank() && !isThinking
            IconButton(
                onClick = {
                    if (canSend) {
                        onSendTask(queryText.trim())
                        queryText = ""
                        focusManager.clearFocus()
                    }
                },
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (canSend) Color(0xFF0969DA) else Color.White.copy(alpha = 0.1f))
                    .testTag("chat_send_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send instruction",
                    tint = if (canSend) Color.White else Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
