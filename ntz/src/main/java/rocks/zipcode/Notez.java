package rocks.zipcode;

import java.util.Arrays;

/**
 * ntz main command.
 */
public final class Notez {

    private FileMap filemap;

    public Notez() {
        this.filemap  = new FileMap();
    }
    ///**
     //* Says hello to the world.
     //*
     //* @param args The arguments of the program.
     //*/
    public static void main(String argv[]) {
        boolean _debug = true;
        // for help in handling the command line flags and data!
        if (_debug) {
            System.err.print("Argv: [");
            for (String a : argv) {
                System.err.print(a+" ");
            }
            System.err.println("]");
        }

        Notez ntzEngine = new Notez();

        ntzEngine.loadDatabase();

        /*
         * You will spend a lot of time right here.
         *
         * instead of loadDemoEntries, you will implement a series
         * of method calls that manipulate the Notez engine.
         * See the first one:
         */
        //ntzEngine.loadDemoEntries();

        //ntzEngine.saveDatabase();

        if (argv.length == 0) { // there are no commandline arguments
            //just print the contents of the filemap.
            ntzEngine.printResults();
        } else {
            if (argv[0].equals("-r")) {
                ntzEngine.addToCategory("General", argv);
            } // this should give you an idea about how to TEST the Notez engine
              // without having to spend lots of time messing with command line arguments.
        }

        if(argv.length == 0){
            ntzEngine.printResults();
        }else{
            if(argv[0].equals("-c")){
                ntzEngine.createAppendCategory("General", argv);
            }
        }

        if(argv.length == 0){
            ntzEngine.printResults();
        }else{
            if(argv[0].equals("-f")){
                String indexStr = argv[1];
                ntzEngine.forgetNote(indexStr);
            }
        }

        if(argv.length == 0){
            ntzEngine.printResults();
        }else{
            if(argv[0].equals("-e")){
                ntzEngine.replaceNote("Replaced Note", Arrays.toString(argv));
            }
        }

        ntzEngine.saveDatabase();
        /*
         * what other method calls do you need here to implement the other commands??
         */

    }

    private void addToCategory(String string, String[] argv) {
        if(argv.length == 0){
            System.out.println("No items to add to category " + string);
        }
        //Check if category already exists in filemap
        if(!filemap.containsKey(string)){
            filemap.put(string, new NoteList(string));
        }

        NoteList noteList = filemap.get(string);

        //add each item to the noteList
        noteList.addAll(Arrays.asList(argv));
        saveDatabase();
    }

    private void saveDatabase() {
        filemap.save();
    }

    private void loadDatabase() {
        filemap.load();
    }

    public void printResults() {
        System.out.println(this.filemap.toString());
    }

    public void loadDemoEntries() {
        filemap.put("General", new NoteList("The Very first Note"));
        filemap.put("note2", new NoteList("A secret second note"));
        filemap.put("category3", new NoteList("Did you buy bread AND eggs?"));
        filemap.put("anotherNote", new NoteList("Hello from ZipCode!"));
    }
    /*
     * Put all your additional methods that implement commands like forget here...
     */
    public void createAppendCategory(String str, String[] argv){
        if(argv.length == 0){
            System.out.println("Nothing to create or append " + str);
        }

        //Check if category already exists in filemap
        if(!filemap.containsKey(str)){
            filemap.put(str, new NoteList(str));
        }

        NoteList noteList = filemap.get(str);

        //Add each item to the noteList
        noteList.addAll(Arrays.asList(argv));

        //Save DB after adding items
        saveDatabase();
    }

    public void replaceNote(String indKey, String newNote){
        if(!filemap.containsKey(indKey)){
            System.out.println("Invalid note index.");
        }
        //Get the notelist at the specified index
        NoteList noteList = filemap.get(indKey);
        if(noteList != null){
            noteList.set(Integer.parseInt(indKey), newNote);
            System.out.println("Replaced note");
        }else{
            System.out.println("No Note found at index");
        }

        saveDatabase();
    }

    public void forgetNote(String indexStr){
        if(!filemap.containsKey(indexStr)){
            System.out.println("Invalid note index.");
        }

        NoteList noteList = filemap.get(indexStr);

        if(noteList != null){
            noteList.remove(indexStr);
            System.out.println("Note forgotten");
        }else{
            System.out.println("No note found at index");
        }
        saveDatabase();
    }

}
