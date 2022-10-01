package com.kotlinlearn.drugstore.utils

//fun Dialog.makeDialog(list:ArrayList<Any>,context: Context,index :Int){
//    var bulider = AlertDialog.Builder(context)
//    bulider.apply {
//        setTitle("Confirmation ")
//        setMessage("Are you sure to delete this item ?")
//        setIcon(R.drawable.ic_pharmacy_icon_svgrepo_com)
//        setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
//            Toast.makeText(context, "Deleted Successfully ", Toast.LENGTH_SHORT).show()
//            list.remove(list[index])
//        //    notifyDataSetChanged()
//        })
//        setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
//
//        })
//
//    }
//    bulider.create()
//    bulider.show()
//}