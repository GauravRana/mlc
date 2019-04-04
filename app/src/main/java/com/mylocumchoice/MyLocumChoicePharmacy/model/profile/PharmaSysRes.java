package com.mylocumchoice.MyLocumChoicePharmacy.model.profile;

import java.util.ArrayList;

public class PharmaSysRes {

    private ArrayList<System> systems;

    public ArrayList<System> getSystems() { return this.systems; }

    public void setSystems(ArrayList<System> systems) { this.systems = systems; }

    public class System
    {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private boolean selected;

        public boolean getSelected() { return this.selected; }

        public void setSelected(boolean selected) { this.selected = selected; }
    }

}
