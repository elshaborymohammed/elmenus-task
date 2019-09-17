package com.elmenus.task.presentation.items

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.elmenus.base.app.AppFragment
import com.elmenus.task.R
import com.elmenus.task.domain.model.Item
import com.elmenus.task.domain.model.Tag
import com.elmenus.task.presentation.tag.TagAdapter
import com.elmenus.task.presentation.tag.TagViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_items.view.*
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class ItemsFragment : AppFragment, TagAdapter.OnClickListener, TagAdapter.OnLoadMoreListener,
    ItemAdapter.OnClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var tagViewModel: TagViewModel
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var tagAdapter: TagAdapter
    private lateinit var itemAdapter: ItemAdapter
    private var disposableItems: Disposable? = null

    constructor() {
        Log.i("ItemsFragment", "constructor")
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_items
    }

    override fun onViewBound(view: View) {
        tagViewModel =
            ViewModelProviders.of(requireActivity(), factory).get(TagViewModel::class.java)
        itemViewModel =
            ViewModelProviders.of(requireActivity(), factory).get(ItemViewModel::class.java)

        tagAdapter = TagAdapter()
        tagAdapter.setOnClickListener(this)
        tagAdapter.setOnLoadMoreListener(this)
        view.tagList.adapter = tagAdapter

        itemAdapter = ItemAdapter()
        itemAdapter.setOnClickListener(this)
        view.itemList.adapter = itemAdapter
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            tagViewModel.loading().subscribe(onLoading(), onError()),
            itemViewModel.loading().subscribe(onLoading(), onError()),
            fetchTags()
        )
    }

    override fun onClickTag(obj: Tag) {
        disposableItems?.dispose()
        itemAdapter.swap(null)
        fetchItems(obj.name)
    }

    override fun onLoadMore(page: Int) {
        subscribe(fetchTags(page))
    }

    override fun onClickItem(obj: Item, imageView: ImageView) {
        imageView.transitionName = "imageView"
        findNavController().navigate(
            ItemsFragmentDirections.actionHomeFragmentToItemDetailsFragment(
                obj.name
            ),
            FragmentNavigatorExtras(imageView to "imageView")
        )
    }

    override fun onError(): Consumer<Throwable> {
        return Consumer {
            it.printStackTrace()
            super.onError().accept(it)
            onLoading().accept(false)
        }
    }

    private fun onLoading(): Consumer<Boolean> {
        return Consumer {
            activity?.runOnUiThread {
                view?.loadingBar?.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun fetchTags(): Disposable {
        return tagViewModel.get()
            .subscribe(Consumer {
                Log.i("ItemsFragment", "$it")
                tagAdapter.swap(it)
                fetchItems(it[0].name)
            }, onError())
    }

    private fun fetchTags(page: Int): Disposable {
        return tagViewModel.fetch(page).subscribe(Consumer { tagAdapter.addAll(it) }, onError())
    }

    private fun fetchItems(tagName: String) {
        return itemViewModel.get(tagName).observe(this, Observer {
            Log.i("ItemsFragment", "$it")
            itemAdapter.swap(it)
        })
    }

    override fun onDestroyView() {
        disposableItems?.dispose()
        super.onDestroyView()
    }
}