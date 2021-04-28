package model;

import java.sql.*;
import java.util.List;

class SqLiteBoardDao implements Dao<Board> {
    private final Connection conn;

    private static final String SQL_GET_BY_ID = "SELECT * FROM Board WHERE board_id = ?";
    private static final String SQL_UPDATE = "UPDATE Board SET title = ? WHERE board_id = ?";

    public SqLiteBoardDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Board get(int id) {
        Board board = null;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_BY_ID);
            preparedStatement.setInt(1, id);

            var res = preparedStatement.executeQuery();

            if (res.next()) {
                board = getInstance(res);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return board;
    }

    @Override
    public List<Board> get_all(int id) {
        throw new UnsupportedOperationException("Il n'y a qu'un board");
    }

    @Override
    public Board save(Board board) {
        throw new UnsupportedOperationException("Il n'y a qu'un board");
    }

    @Override
    public void update(Board board) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE);
            setValues(board, preparedStatement);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La mise à jour du board a échoué: aucune colonne modifiée.");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Board board) {
        throw new UnsupportedOperationException("Impossible de supprimer le board");
    }

    private void setValues(Board board, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, board.getTitle());
    }

    private Board getInstance(ResultSet res) throws SQLException {
        var id = res.getInt(1);
        var title = res.getString(2);
        return new Board(id, title);
    }
}
