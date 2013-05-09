interface NotebookDb {
    boolean isNameExists(final String name) throws Exception;

    void addRecord(final String name, final String phone) throws Exception;

    void remove(final String name) throws Exception;

    String searchByName(final String name) throws Exception;

    String searchByPhone(final String phone) throws Exception;

    String Open() throws Exception;
}
