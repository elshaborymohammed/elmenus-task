package com.elmenus.task.presentation.items

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.elmenus.base.app.AppActivity
import com.elmenus.task.R
import com.elmenus.task.app.GlideApp
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_details_item.*
import javax.inject.Inject

class ItemDetailsActivity : AppActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var itemViewModel: ItemViewModel

    override fun layoutRes(): Int {
        return R.layout.activity_details_item
    }

    override fun onCreate() {
        postponeEnterTransition()
        startPostponedEnterTransition()
        itemViewModel = ViewModelProviders.of(this, factory).get(ItemViewModel::class.java)

        setSupportActionBar(toolbar)
        toolbar?.setNavigationOnClickListener { supportFinishAfterTransition() }

        intent.getStringExtra("name").let { name ->
            subscribe(
                itemViewModel?.getOne(name)
                    .subscribe(Consumer {
                        toolbar_layout?.title = it.name
                        description?.text = it.description
                        image?.let { imageView ->
                            GlideApp
                                .with(this)
                                .load(it.photo)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView)
                        }
                    }, onError())
            )
        }
    }
}
