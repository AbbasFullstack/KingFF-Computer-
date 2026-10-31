package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ComputerTab
import com.example.ui.components.BrowserView
import com.example.ui.components.ChatInputBar
import com.example.ui.components.TerminalView
import com.example.ui.components.TimelineControls
import com.example.ui.components.TopHeader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun KingFFComputerApp() {
    var currentTab by remember { mutableStateOf(ComputerTab.BROWSER) }
    var timelineProgress by remember { mutableFloatStateOf(0.87f) }
    var isLive by remember { mutableStateOf(false) }
    var isThinking by remember { mutableStateOf(false) }

    var statusTitle by remember { mutableStateOf("KingFF is using Browser") }
    var statusSubtext by remember { mutableStateOf("Browsing: https://github.com/storybookjs/story...") }
    var browserUrl by remember { mutableStateOf("github.com") }
    var terminalCommand by remember { mutableStateOf("pip install playwright") }
    var extraTerminalLogs by remember { mutableStateOf(listOf<String>()) }
    var activeCursorAction by remember { mutableStateOf("Reading Docs") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun updateStateForTab(tab: ComputerTab) {
        currentTab = tab
        if (tab == ComputerTab.BROWSER) {
            statusTitle = "KingFF is using Browser"
            statusSubtext = "Browsing: https://github.com/storybookjs/story..."
        } else {
            statusTitle = "KingFF is using Terminal"
            statusSubtext = "Running: $terminalCommand"
        }
    }

    // Handles user sending a task via chat or quick chip
    fun handleUserTask(task: String) {
        scope.launch {
            isThinking = true
            statusTitle = "KingFF is thinking..."
            statusSubtext = "Analyzing instruction: \"$task\""
            timelineProgress = 0.95f
            isLive = true

            delay(1600)
            isThinking = false

            when {
                task.contains("FaucetPay", ignoreCase = true) -> {
                    currentTab = ComputerTab.BROWSER
                    browserUrl = "faucetpay.io/dashboard"
                    statusTitle = "KingFF is using Browser"
                    statusSubtext = "Navigating: https://faucetpay.io/dashboard/user"
                    activeCursorAction = "Verifying Balances"
                    timelineProgress = 1.0f
                    snackbarHostState.showSnackbar("KingFF: Navigated to FaucetPay Dashboard")
                }
                task.contains("Search", ignoreCase = true) || task.contains("GitHub", ignoreCase = true) -> {
                    currentTab = ComputerTab.BROWSER
                    browserUrl = "github.com/search?q=kingff"
                    statusTitle = "KingFF is using Browser"
                    statusSubtext = "Querying: github.com/search?q=kingff-ai-bot"
                    activeCursorAction = "Parsing Repositories"
                    timelineProgress = 1.0f
                    snackbarHostState.showSnackbar("KingFF: Found 3 matching GitHub repositories")
                }
                task.contains("playwright", ignoreCase = true) -> {
                    currentTab = ComputerTab.TERMINAL
                    terminalCommand = "pytest tests/test_playwright.py -v"
                    extraTerminalLogs = extraTerminalLogs + listOf(
                        "[KingFF] Launching headless chromium...",
                        "test_login_flow PASSED [100%]",
                        "✓ 1 passed in 1.42s"
                    )
                    statusTitle = "KingFF is using Terminal"
                    statusSubtext = "Running: pytest tests/test_playwright.py"
                    timelineProgress = 1.0f
                    snackbarHostState.showSnackbar("KingFF: Playwright tests executed successfully")
                }
                else -> {
                    currentTab = ComputerTab.TERMINAL
                    terminalCommand = "python3 agent_run.py --task \"$task\""
                    extraTerminalLogs = extraTerminalLogs + listOf(
                        "[KingFF-Task] Registered: $task",
                        "[Planner] Decomposing workflow into 3 sub-actions",
                        "● Executing step 1 via AI Browser runtime..."
                    )
                    statusTitle = "KingFF is using Terminal"
                    statusSubtext = "Running: python3 agent_run.py"
                    timelineProgress = 1.0f
                    snackbarHostState.showSnackbar("KingFF: Executing \"$task\"")
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEDEEF0)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            val isWideScreen = maxWidth > 600.dp

            // Centered phone-sized container (max 420dp width) with rounded corners and shadow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(if (isWideScreen) 24.dp else 0.dp)
                    .widthIn(max = 420.dp)
                    .clip(if (isWideScreen) RoundedCornerShape(28.dp) else RoundedCornerShape(0.dp))
                    .background(Color(0xFF101010))
                    .then(
                        if (isWideScreen) {
                            Modifier
                                .shadow(24.dp, RoundedCornerShape(28.dp))
                                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(28.dp))
                        } else Modifier
                    )
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // 1. Top Header: "KingFF's computer" with Close X and Monitor dropdown
                    TopHeader(
                        onCloseClick = {
                            scope.launch {
                                timelineProgress = 0.87f
                                isLive = false
                                updateStateForTab(ComputerTab.BROWSER)
                                snackbarHostState.showSnackbar("Session reconnected to KingFF computer")
                            }
                        }
                    )

                    // 2. Tab Switcher (Browser vs Terminal)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF101010))
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.06f))
                                .padding(3.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            // Browser Tab
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (currentTab == ComputerTab.BROWSER) Color.White else Color.Transparent)
                                    .clickable { updateStateForTab(ComputerTab.BROWSER) }
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                                    .testTag("tab_browser_button"),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Language,
                                    contentDescription = null,
                                    tint = if (currentTab == ComputerTab.BROWSER) Color.Black else Color.White.copy(alpha = 0.6f),
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Browser",
                                    color = if (currentTab == ComputerTab.BROWSER) Color.Black else Color.White.copy(alpha = 0.6f),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            // Terminal Tab
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (currentTab == ComputerTab.TERMINAL) Color.White else Color.Transparent)
                                    .clickable { updateStateForTab(ComputerTab.TERMINAL) }
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                                    .testTag("tab_terminal_button"),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Terminal,
                                    contentDescription = null,
                                    tint = if (currentTab == ComputerTab.TERMINAL) Color.Black else Color.White.copy(alpha = 0.6f),
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Terminal",
                                    color = if (currentTab == ComputerTab.TERMINAL) Color.Black else Color.White.copy(alpha = 0.6f),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    // 3. Main Views (Browser / Terminal) inside weighted container
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(Color(0xFF101010))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        AnimatedContent(
                            targetState = currentTab,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "tab_animation"
                        ) { tab ->
                            when (tab) {
                                ComputerTab.BROWSER -> BrowserView(
                                    browserUrl = browserUrl,
                                    cursorAction = activeCursorAction,
                                    modifier = Modifier.fillMaxSize()
                                )
                                ComputerTab.TERMINAL -> TerminalView(
                                    activeCommand = terminalCommand,
                                    extraLogs = extraTerminalLogs,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    // 4. Status Bar & Timeline Controls (Skip 10s, Scrubber, Jump to live)
                    TimelineControls(
                        currentTab = currentTab,
                        statusTitle = statusTitle,
                        statusSubtext = statusSubtext,
                        progress = timelineProgress,
                        isLive = isLive || timelineProgress >= 0.98f,
                        onProgressChange = { newProgress ->
                            timelineProgress = newProgress
                            isLive = newProgress >= 0.98f
                            // Dynamic snapshot changes based on scrubber seek position
                            if (newProgress < 0.4f) {
                                currentTab = ComputerTab.TERMINAL
                                statusTitle = "KingFF is using Terminal"
                                statusSubtext = "Running: ./run.sh --browser --terminal"
                            } else if (newProgress < 0.75f) {
                                currentTab = ComputerTab.TERMINAL
                                statusTitle = "KingFF is using Terminal"
                                statusSubtext = "Running: pip install playwright"
                            } else {
                                currentTab = ComputerTab.BROWSER
                                statusTitle = "KingFF is using Browser"
                                statusSubtext = "Browsing: https://github.com/storybookjs/story..."
                            }
                        },
                        onSkipBack = {
                            val newP = (timelineProgress - 0.15f).coerceAtLeast(0f)
                            timelineProgress = newP
                            isLive = false
                            if (newP < 0.5f) {
                                updateStateForTab(ComputerTab.TERMINAL)
                            }
                        },
                        onSkipForward = {
                            val newP = (timelineProgress + 0.15f).coerceAtMost(1f)
                            timelineProgress = newP
                            isLive = newP >= 0.98f
                        },
                        onJumpToLive = {
                            timelineProgress = 1.0f
                            isLive = true
                            updateStateForTab(currentTab)
                        }
                    )

                    // 5. Chat Input Bar (Bottom input with blue arrow and task chips)
                    ChatInputBar(
                        isThinking = isThinking,
                        onSendTask = { taskPrompt ->
                            handleUserTask(taskPrompt)
                        }
                    )
                }

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 80.dp)
                )
            }
        }
    }
}
