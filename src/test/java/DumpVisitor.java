import com.moybl.restql.ast.*;

public class DumpVisitor implements Visitor {

	private int ident;

	public void visit(Literal acceptor) {
		print(acceptor);
	}

	public void visit(Identifier acceptor) {
		print(acceptor);
	}

	public void visit(UnaryOperation acceptor) {
		print(acceptor);
		ident++;
		acceptor.getChild().accept(this);
		ident--;
	}

	public void visit(BinaryOperation acceptor) {
		print(acceptor);
		ident++;
		acceptor.getLeft().accept(this);
		acceptor.getRight().accept(this);
		ident--;
	}

	private void print(Object obj) {
		String message = obj.toString();

		for (int i = 0; i < ident * 4; i++) {
			message = " " + message;
		}

		System.out.println(message);
	}

}
