package com.elmenus.task.presentation.home

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.elmenus.base.app.AppActivity
import com.elmenus.task.R
import com.elmenus.task.app.SnakBarHelper
import com.elmenus.task.domain.model.Item
import com.elmenus.task.domain.model.Tag
import com.elmenus.task.presentation.items.ItemAdapter
import com.elmenus.task.presentation.items.ItemDetailsActivity
import com.elmenus.task.presentation.items.ItemViewModel
import com.elmenus.task.presentation.tag.TagAdapter
import com.elmenus.task.presentation.tag.TagViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_home.*
import java.io.IOException
import javax.inject.Inject

class HomeActivity : AppActivity(), TagAdapter.OnClickListener, TagAdapter.OnLoadMoreListener,
    ItemAdapter.OnClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var tagViewModel: TagViewModel
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var tagAdapter: TagAdapter
    private lateinit var itemAdapter: ItemAdapter
    private var disposableItems: Disposable? = null

    override fun layoutRes(): Int {
        return R.layout.activity_home
    }

    override fun onCreate() {
        tagViewModel = ViewModelProviders.of(this, factory).get(TagViewModel::class.java)
        itemViewModel = ViewModelProviders.of(this, factory).get(ItemViewModel::class.java)

        tagAdapter = TagAdapter()
        tagAdapter.setOnClickListener(this)
        tagAdapter.setOnLoadMoreListener(this)
        tagList.adapter = tagAdapter

        itemAdapter = ItemAdapter()
        itemAdapter.setOnClickListener(this)
        itemList.adapter = itemAdapter
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            tagViewModel.loading().subscribe(onLoading(), super.onError()),
            itemViewModel.loading().subscribe(onLoading(), super.onError()),
            fetchTags()
        )
    }

    override fun onClickTag(obj: Tag) {
        disposableItems?.dispose()

        itemAdapter.swap(null)
        obj.name?.let { disposableItems = fetchItems(it) }
    }

    override fun onLoadMore(page: Int) {
        subscribe(fetchTags(page))
    }

    override fun onClickItem(obj: Item, imageView: ImageView) {
        val intent = Intent(this, ItemDetailsActivity::class.java)
        intent.putExtra("name", obj.name)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView as View, "imageView")
        startActivity(intent, options.toBundle())
    }

    private fun onLoadingItemsError(): Consumer<Throwable> {
        return Consumer { throwable ->
            onLoading().accept(false)
            if (throwable is IOException) {
                SnakBarHelper.retry(itemList, View.OnClickListener { _ ->
                    subscribe(
                        itemViewModel.getByLastTagSelected()?.subscribe(Consumer {
                            itemAdapter.swap(it)
                        }, onLoadingItemsError())
                    )
                })
            } else {
                super.onError().accept(throwable)
            }
        }
    }

    private fun onLoading(): Consumer<Boolean> {
        return Consumer {
            runOnUiThread {
                loadingBar?.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun fetchTags(): Disposable {
        return tagViewModel.get()
            .subscribe(Consumer {
                tagAdapter.swap(it)
                disposableItems = fetchItems(it[0].name)
            }, super.onError())
    }

    private fun fetchTags(page: Int): Disposable {
        return tagViewModel.fetch(page).subscribe(Consumer { tagAdapter.addAll(it) },
            Consumer {
                tagAdapter.noMoreDataWithError()
                super.onError().accept(it)
            })
    }

    private fun fetchItems(tagName: String): Disposable {
        return itemViewModel.get(tagName).subscribe(Consumer {
            itemAdapter.swap(it)
        }, onLoadingItemsError())
    }

    override fun onDestroy() {
        disposableItems?.dispose()
        super.onDestroy()
    }
}
