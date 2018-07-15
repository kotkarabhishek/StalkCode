package com.example.kotkarandsons.stalkcode;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CodeHolder>{

    public List<CodeInfo> codelist;
    public CodeAdapter(List<CodeInfo> codelist)
    {
        this.codelist=codelist;
    }
    @NonNull
    @Override
    public CodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.codelayout,parent,false);
        return new CodeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeHolder holder, int position) {
        CodeInfo newCode=codelist.get(position);
        holder.codeTitle.setText(newCode.getCodeName());
        holder.codeDate.setText(newCode.getDate());
        holder.codeLevel.setText(newCode.getCodeLevel());
        holder.codeStatus.setText(newCode.getCodeStatus());
        holder.codeType.setText(newCode.getCodeType());
    }

    @Override
    public int getItemCount() {
        return codelist.size();
    }

    public  class CodeHolder extends RecyclerView.ViewHolder
    {
        TextView codeTitle,codeType,codeLevel,codeDate,codeStatus;

        public CodeHolder(View itemView) {
            super(itemView);
            codeTitle=(TextView)itemView.findViewById(R.id.titlecodecard);
            codeType=(TextView)itemView.findViewById(R.id.typecodecard);
            codeLevel=(TextView)itemView.findViewById(R.id.levelcodecard);
            codeDate=(TextView)itemView.findViewById(R.id.datecodecard);
            codeStatus=(TextView)itemView.findViewById(R.id.statuscodecard);
        }
    }
}
