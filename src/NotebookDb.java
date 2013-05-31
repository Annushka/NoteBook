interface NotebookDb {
    boolean isNameExists(final String name) throws Exception;

    void addRecord(final String data) throws Exception;

    void remove(final String name) throws Exception;

    String searchByName(final String name) throws Exception;

    String searchByPhone(final String phone) throws Exception;

    void Open() throws Exception;
}
