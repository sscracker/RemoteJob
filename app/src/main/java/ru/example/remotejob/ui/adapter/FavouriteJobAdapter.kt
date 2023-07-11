package ru.example.remotejob.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import ru.example.remotejob.databinding.ItemJobBinding
import ru.example.remotejob.models.FavouriteJob
import ru.example.remotejob.models.Job
import ru.example.remotejob.ui.fragments.MainFragmentDirections

class FavouriteJobAdapter constructor(
    private val itemClick: OnItemClickListener
):RecyclerView.Adapter<FavouriteJobAdapter.RemoteJobViewHolder>(){


    private var binding: ItemJobBinding? = null

    class RemoteJobViewHolder(val binding: ItemJobBinding) : ViewHolder(binding.root)


      private val diffCallback = object : DiffUtil.ItemCallback<FavouriteJob>(){
          override fun areItemsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
              return oldItem.url == newItem.url
          }

          override fun areContentsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
              return oldItem == newItem
          }


      }
    val diff = AsyncListDiffer(this@FavouriteJobAdapter,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
       // return RemoteJobViewHolder(ItemJobBinding.inflate(LayoutInflater.from(parent.context),parent,false)!!)
        binding = ItemJobBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RemoteJobViewHolder(binding!!)
    }

    override fun getItemCount() = diff.currentList.size

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        val currentJob = diff.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(currentJob.companyLogoUrl)
                .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType
            binding?.ibDelete?.visibility = View.VISIBLE

            val date = currentJob.publicationDate?.split("T")
            binding?.tvDate?.text = date?.get(0)

        }.setOnClickListener { view ->
            val tags = arrayListOf<String>()
            val job = Job(
               currentJob.candidateRequiredLocation,
               currentJob.category,
               currentJob.companyLogoUrl,
               currentJob.companyName,
               currentJob.description,
               currentJob.id,
               currentJob.jobType,
               currentJob.publicationDate,
               currentJob.salary,
                tags,
               currentJob.title,
                currentJob.url
            )
            val direction = MainFragmentDirections.actionMainFragmentToJobDetailsFragment(job)

            view.findNavController().navigate(direction)
        }

        holder.itemView.apply {
            binding?.ibDelete?.setOnClickListener {
                itemClick.onItemClick(currentJob,binding?.ibDelete!!,position)
            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(
            job: FavouriteJob,
            view: View,
            position: Int
        )
    }
}