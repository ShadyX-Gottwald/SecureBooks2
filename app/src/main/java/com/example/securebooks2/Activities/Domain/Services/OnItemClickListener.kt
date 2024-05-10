package com.example.securebooks2.Activities.Domain.Services

interface OnItemClickListener<T> {

  fun  onItemClicked(item: T)
}