package com.handcontrol.ui.main.gesturedetails

import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.handcontrol.R
import com.handcontrol.base.BaseFragment
import com.handcontrol.base.BaseRecyclerAdapter
import com.handcontrol.databinding.FragmentGestureDetailsEditorBinding
import com.handcontrol.model.Action
import com.handcontrol.model.ExecutableItem
import com.handcontrol.model.Gesture
import com.handcontrol.ui.main.action.ActionFragment
import com.handcontrol.ui.main.gesturedetails.GestureDetailsFragment.Companion.ARG_GESTURE_KEY
import com.handcontrol.ui.main.gestures.ExecutableItemListener
import kotlinx.android.synthetic.main.fragment_gesture_details_editor.*

class GestureDetailsEditorFragment
    : BaseFragment<FragmentGestureDetailsEditorBinding, GestureDetailsViewModel>(
    GestureDetailsViewModel::class.java,
    R.layout.fragment_gesture_details_editor
) {

//    private val PERMISSIONS_RECORD_AUDIO = 200

    override val viewModel: GestureDetailsViewModel by navGraphViewModels(R.id.nav_graph_gesture) {
        GestureDetailsViewModelFactory(
            arguments?.getSerializable(ARG_GESTURE_KEY) as? Gesture
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingAddActionButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.nav_graph_action)
        }
        with(editableActionsRecycler) {
            adapter = BaseRecyclerAdapter<Action, ExecutableItemListener>(
                R.layout.list_item_editable,
                viewModel.actions.value!!,
                object : ExecutableItemListener {
                    override fun onClick(item: ExecutableItem, position: Int) {
                        val navController =
                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        navController.navigate(R.id.nav_graph_action, Bundle().apply {
                            putSerializable(ActionFragment.ARG_ACTION_KEY, item)
                            putSerializable(ActionFragment.ARG_ACTION_POSITION, position)
                        })
                    }

                    override fun onPlay(item: ExecutableItem, position: Int) {}

                }
            )

            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.storable_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return when (item.itemId) {
            R.id.app_bar_save -> {
                viewModel.saveGesture()
                navController.popBackStack()
                true
            }
            else -> item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }


    //  Вставьте фрагмент кода, когда пользователь захочет сохранить график
//    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    == PackageManager.PERMISSION_DENIED
//    ) {
//        // Запрос разрешения
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//            PERMISSIONS_WRITE_EXTERNAL_STORAGE
//        )
//    } else {
//        //исполняемый код
//    }
}