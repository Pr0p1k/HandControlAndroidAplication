package com.handcontrol.ui.main.gestures

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.handcontrol.R
import com.handcontrol.base.BaseFragment
import com.handcontrol.base.BaseRecyclerAdapter
import com.handcontrol.databinding.FragmentGesturesBinding
import com.handcontrol.model.ExecutableItem
import com.handcontrol.model.Gesture
import com.handcontrol.ui.main.gesturedetails.GestureDetailsFragment
import com.handcontrol.ui.main.gesturedetails.GestureDetailsFragment.Companion.ARG_NEW_GESTURE_NAME
import kotlinx.android.synthetic.main.fragment_gestures.*

class GesturesFragment : BaseFragment<FragmentGesturesBinding, GesturesViewModel>(
    GesturesViewModel::class.java,
    R.layout.fragment_gestures
) {

//    private val PERMISSIONS_RECORD_AUDIO = 200

    override val viewModel: GesturesViewModel by lazy { ViewModelProvider(this).get(viewModelClass) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingActionButton.setOnClickListener {

            val navController = findNavController()
            navController.navigate(R.id.nav_graph_gesture,
                Bundle().apply {
                    putString("title", ARG_NEW_GESTURE_NAME)
                })
        }

        with(gestureRecycler) {
            adapter = BaseRecyclerAdapter<Gesture, ExecutableItemListener>(
                R.layout.list_item_executable,
                viewModel.listData.value!!,
                object : ExecutableItemListener {
                    override fun onClick(item: ExecutableItem, position: Int) {
                        val navController =
                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        navController.navigate(R.id.nav_graph_gesture, Bundle().apply {
                            putSerializable(GestureDetailsFragment.ARG_GESTURE_KEY, item)
                            putString("title", item.name)
                        })
                    }

                    override fun onPlay(item: ExecutableItem, position: Int) {
                        //todo get gesture state from api
                        item.isExecuted = !item.isExecuted
                        viewModel.performGesture(item.id!!)
                        gestureRecycler.adapter?.notifyDataSetChanged()
                    }

                }
            )

            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }


//  Вставьте фрагмент кода, когда пользователь захочет использовать голосовые команды
//    if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//    == PackageManager.PERMISSION_DENIED
//    ) {
//        // Запрос разрешения
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.RECORD_AUDIO),
//            PERMISSIONS_RECORD_AUDIO
//        )
//    } else {
//        //исполняемый код
//    }
}