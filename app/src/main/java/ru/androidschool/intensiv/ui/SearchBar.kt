package ru.androidschool.intensiv.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.SearchToolbarBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.feed.FeedFragment
import java.util.concurrent.TimeUnit

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    lateinit var binding: SearchToolbarBinding

    private var hint: String = ""
    private var isCancelVisible: Boolean = true

    init {
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.SearchBar).apply {
                hint = getString(R.styleable.SearchBar_hint).orEmpty()
                isCancelVisible = getBoolean(R.styleable.SearchBar_cancel_visible, true)
                recycle()
            }
        }
    }

    fun setText(text: String?) {
        binding.searchEditText.setText(text)
    }

    fun clear() {
        binding.searchEditText.setText("")
    }

    val onTextChangedObservable by lazy {
        Observable.create(
            ObservableOnSubscribe<String> { subscriber ->
                binding.searchEditText.doAfterTextChanged { text ->
                    subscriber.onNext(text.toString())
                }
            }
        )
    }

    val onTextChangedWithOperatorObservable by lazy {
        onTextChangedObservable
            .debounce(timeOfPause, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .filter { it.length > FeedFragment.MIN_LENGTH }
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = SearchToolbarBinding.inflate(LayoutInflater.from(context), this, true)
        binding.searchEditText.hint = hint
        binding.deleteTextButton.setOnClickListener {
            binding.searchEditText.text.clear()
        }
//        binding.searchEditText.textChanges()
//            .debounce(timeOfPause, java.util.concurrent.TimeUnit.MILLISECONDS)
//                //не понял как сделать через filter((
//            .subscribe(
//                {
//                    if(it != null) {
//                        if(it.length > FeedFragment.MIN_LENGTH) {
//                            Log.e("check count", "текст больше 3 символов")
//                            val modifyTextValue = it.toString().replace(" ", "")
//                            Log.e("onFinish", "$modifyTextValue\nОтправляй запрос!")
//                            val getSearchResult = MovieApiClient.apiClient.getSearchResult(query = it.toString())
//                        }
//                    }
//                },
//                {error->
//                    Log.e("error Debounce:", "$error")
//                }
//            )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.searchEditText.afterTextChanged { text ->
            if (!text.isNullOrEmpty() && !binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.VISIBLE
            }
            if (text.isNullOrEmpty() && binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.GONE
            }
        }
    }

    companion object {
        val timeOfPause: Long = 500
    }
}
