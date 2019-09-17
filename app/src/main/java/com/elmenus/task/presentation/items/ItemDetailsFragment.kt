package com.elmenus.task.presentation.items

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.elmenus.base.app.AppFragment
import com.elmenus.task.R
import com.elmenus.task.app.GlideApp
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_item_details.view.*
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class ItemDetailsFragment : AppFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var itemViewModel: ItemViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_item_details
    }

    override fun onViewBound(view: View) {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        itemViewModel = ViewModelProviders.of(this, factory).get(ItemViewModel::class.java)

        view.toolbar?.let {
            val activity = activity as AppCompatActivity
            activity.setSupportActionBar(it)
            activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            it.setNavigationOnClickListener { activity.onBackPressed() }
        }
        arguments?.let { bundle ->
            subscribe(
                itemViewModel.getOne(ItemDetailsFragmentArgs.fromBundle(bundle).name)
                    .subscribe(Consumer {
                        view.toolbar_layout?.title = it.name
                        view.description?.text = it.description
                        view.image.let { imageView ->
                            //                            imageView.transitionName = "imageView" + it.id
                            GlideApp
                                .with(this)
                                .load(it.photo)
                                .into(imageView)
                        }
                    }, onError())
            )
        }
    }

    override fun onError(): Consumer<Throwable> {
        return Consumer {
            it.printStackTrace()
            super.onError().accept(it)
        }
    }
}