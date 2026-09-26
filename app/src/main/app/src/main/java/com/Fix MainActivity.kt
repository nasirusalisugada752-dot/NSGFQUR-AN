package com.nasirusalisu.nsgalquran

import android.app.Activity
import android.os.Bundle
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private val PREFS_NAME = "quran_progress"
    private val PAGE_KEY = "last_page"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showHomeScreen()
    }

    private fun showHomeScreen() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lastPage = prefs.getInt(PAGE_KEY, 0)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(40, 40, 40, 40)

        val title = TextView(this)
        title.text = "NSG Al-Qur'an"
        title.textSize = 28f
        title.setTypeface(null, Typeface.BOLD)
        title.gravity = Gravity.CENTER

        val subtitle = TextView(this)
        subtitle.text = "Karatu • Fahimta • Tunawa"
        subtitle.textSize = 16f
        subtitle.gravity = Gravity.CENTER
        subtitle.setPadding(0, 10, 0, 40)

        val startButton = Button(this)
        startButton.text = "📖 Fara Karatu"

        val continueButton = Button(this)

        if (lastPage > 0) {
            continueButton.text = "▶ Ci gaba da Karatu\nShafi na $lastPage"
            continueButton.isEnabled = true
        } else {
            continueButton.text = "▶ Ci gaba da Karatu"
            continueButton.isEnabled = false
        }

        val bookmarkButton = Button(this)
        bookmarkButton.text = "🔖 Alamomi"

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(startButton)
        layout.addView(continueButton)
        layout.addView(bookmarkButton)

        setContentView(layout)

        startButton.setOnClickListener {
            saveProgress(1)
            showReaderScreen(1)
        }

        continueButton.setOnClickListener {
            if (lastPage > 0) {
                showReaderScreen(lastPage)
            }
        }

        bookmarkButton.setOnClickListener {
            showMessage("Alamomi za mu ƙara su nan gaba.")
        }
    }

    private fun showReaderScreen(page: Int) {
        saveProgress(page)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(30, 30, 30, 30)

        val title = TextView(this)
        title.text = "NSG Al-Qur'an"
        title.textSize = 24f
        title.setTypeface(null, Typeface.BOLD)
        title.gravity = Gravity.CENTER

        val pageText = TextView(this)
        pageText.text = "Shafi na $page"
        pageText.textSize = 22f
        pageText.gravity = Gravity.CENTER
        pageText.setPadding(0, 40, 0, 40)

        val info = TextView(this)
        info.text = "A nan za mu saka shafukan Al-Qur'an."
        info.textSize = 17f
        info.gravity = Gravity.CENTER
        info.setPadding(0, 0, 0, 40)

        val nextButton = Button(this)
        nextButton.text = "➡ Shafi na gaba"

        val backButton = Button(this)
        backButton.text = "⬅ Komawa Home"

        layout.addView(title)
        layout.addView(pageText)
        layout.addView(info)
        layout.addView(nextButton)
        layout.addView(backButton)

        setContentView(layout)

        nextButton.setOnClickListener {
            val nextPage = page + 1
            saveProgress(nextPage)
            showReaderScreen(nextPage)
        }

        backButton.setOnClickListener {
            showHomeScreen()
        }
    }

    private fun saveProgress(page: Int) {
        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(PAGE_KEY, page)
            .apply()
    }

    private fun showMessage(message: String) {
        val text = TextView(this)
        text.text = message
        text.textSize = 18f
        text.gravity = Gravity.CENTER
        setContentView(text)
    }
}
