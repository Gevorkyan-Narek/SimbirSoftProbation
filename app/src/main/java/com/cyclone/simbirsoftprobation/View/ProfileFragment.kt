package com.cyclone.simbirsoftprobation.View

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cyclone.simbirsoftprobation.Presenter.Datas
import com.cyclone.simbirsoftprobation.Presenter.FriendsAdapter
import com.cyclone.simbirsoftprobation.R
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.view.*
import java.time.format.DateTimeFormatter

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    lateinit var profileData: Datas

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        profileData = Datas(resources)
        Glide.with(context!!)
            .load(profileData.person.icon)
            .centerInside()
            .into(view.avatar_profile)

        view.avatar_profile.setOnClickListener { v ->
            val photoDialogFragment = PhotoDialogFragment()
            photoDialogFragment.setTargetFragment(this, PhotoDialogFragment.PICK_PHOTO)
            photoDialogFragment.show(fragmentManager!!, "photoPicker")
        }
        view.profile_name.text = profileData.person.fullName
        view.birth_day.text =
            profileData.person.date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
        view.profession.text = profileData.person.profession
        view.push.isChecked = profileData.person.isPush

        view.recycler_friends.layoutManager = LinearLayoutManager(context)
        view.recycler_friends.adapter = FriendsAdapter(profileData.friendsList)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            PhotoDialogFragment.PICK_PHOTO -> {
                val mCurrentPhotoPath = data?.extras?.get("photo") as Uri
                val inputStream = context?.contentResolver?.openInputStream(mCurrentPhotoPath)
                profileData.person.icon = BitmapFactory.decodeStream(inputStream)
            }
            PhotoDialogFragment.CREATE_PHOTO -> {
                val currentPhotoPath = data?.getStringExtra("path")

                BitmapFactory.decodeFile(currentPhotoPath).also { bitmap ->
                    val matrix = Matrix()
                    val orientation = ExifInterface(currentPhotoPath!!).getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL
                    )
                    matrix.postRotate(
                        when (orientation) {
                            ExifInterface.ORIENTATION_ROTATE_90 -> {
                                90f
                            }
                            ExifInterface.ORIENTATION_ROTATE_180 -> {
                                180f
                            }
                            ExifInterface.ORIENTATION_ROTATE_270 -> {
                                270f
                            }
                            else -> {
                                0f
                            }
                        }
                    )
                    profileData.person.icon = Bitmap.createBitmap(
                        bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
                    )
                }
            }
            PhotoDialogFragment.DELETE_PHOTO -> {
                profileData.person.icon = null
            }
        }
        Glide.with(context!!)
            .load(profileData.person.icon)
            .centerInside()
            .placeholder(R.drawable.user_icon)
            .into(avatar_profile)
    }
}