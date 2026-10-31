package com.example.model

enum class ComputerTab {
    BROWSER,
    TERMINAL
}

data class TimelineSnapshot(
    val progress: Float, // 0.0 to 1.0
    val timeLabel: String,
    val tab: ComputerTab,
    val title: String,
    val subtext: String,
    val browserUrl: String = "github.com",
    val browserFullUrl: String = "https://github.com/storybookjs/storybook",
    val terminalOutput: List<String> = emptyList(),
    val cursorPositionX: Float = 0.5f,
    val cursorPositionY: Float = 0.5f,
    val cursorAction: String = "Browsing"
)

data class TaskSuggestion(
    val title: String,
    val prompt: String,
    val initialTab: ComputerTab,
    val expectedAction: String
)
