package com.cyclone.simbirsoftprobation.View

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.cyclone.simbirsoftprobation.Presenter.Datas
import com.cyclone.simbirsoftprobation.Presenter.HelpsAdapter
import com.cyclone.simbirsoftprobation.R
import kotlinx.android.synthetic.main.help_fragment.view.*

class HelpFragment : Fragment(R.layout.help_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.recycler_kind_of_help.layoutManager = GridLayoutManager(context, 2)
        Handler {
            view.progressBarCategoryHelp.visibility = View.GONE
            view.recycler_kind_of_help.adapter = HelpsAdapter(Datas(resources).categoriesOfHelp)
            true
        }.sendEmptyMessageDelayed(0, 1000)
    }
}