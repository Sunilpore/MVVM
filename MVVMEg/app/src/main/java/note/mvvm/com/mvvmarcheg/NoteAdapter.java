package note.mvvm.com.mvvmarcheg;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    //private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldnNote, @NonNull Note newNote) {
            return oldnNote.getId() == newNote.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {
            //It will always return false due to different refrence of two objects
            //Due to it when we remove one item animation will appear on All the list elements
            //return oldNote.equals(newNote);

            return oldNote.getTitle().equals(newNote.getTitle()) &&
                   oldNote.getDescription().equals(newNote.getDescription()) &&
                   oldNote.getPriority() == newNote.getPriority();
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup vg, int i) {
        View view = LayoutInflater.from(vg.getContext())
                .inflate(R.layout.note_item,vg,false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText( String.valueOf(currentNote.getPriority()) );
    }

    /*@Override
    public int getItemCount() {
        return notes.size();
    }*/

/*    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();  //Later on we replace the notifyDataSetChanged() as another method will be efficient to provide insert,update,animation, etc. of recyclerview

        *//*Problem:- When we make changes to paricular item we don't want to reconstruct whole list again as it happens when we use notifyDataSetChanged();

        Solution:- Use DiffUtil class. Way to use
         1.You can either use it direct here and edit adapter
         2. Use with RecyclerView ListAdapter class. Why????
            Because it implements inbuilt its properties and hence no need to implement DiffUtil class separately.
            Also in DiffUtil method it will compare old and new list item. Hence if list conatins 1000 of items then it take some time and during this time if it runs of mainthread, create problem of ANR.
            Where ListAdapter take care of this problem *//*
    }*/

    public Note getNoteAt(int position){
       // return notes.get(position);
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(view->{
                int position = getAdapterPosition();

                if( listener!=null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(getItem(position));
                }
            });

        }
    }


    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
