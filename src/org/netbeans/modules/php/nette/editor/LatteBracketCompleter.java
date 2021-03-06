/*
 * The MIT license
 *
 * Copyright (c) 2010 Radek Ježdík <redhead@email.cz>, Ondřej Brejla <ondrej@brejla.cz>
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package org.netbeans.modules.php.nette.editor;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.api.KeystrokeHandler;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.php.nette.lexer.LatteTokenId;
import org.netbeans.modules.php.nette.lexer.LatteTopTokenId;
import org.netbeans.modules.php.nette.utils.LexUtils;

/**
 * TODO: when text selected encapsulate it with " ' etc
 * @author Radek Ježdík
 */
public class LatteBracketCompleter implements KeystrokeHandler {

	@Override
	public boolean beforeCharInserted(Document doc, int i, JTextComponent jtc, char ch)
			throws BadLocationException {
		if(!isCompletable(ch)) {
			return false;
		}

		TokenSequence<LatteTokenId> ts = getLatteSequence(doc, i);

		if(ts == null) {
			return false;
		}

		if(ts.token().id() == LatteTokenId.STRING) {
			return false;
		}

		doc.insertString(i, ch + "" + getMatching(ch), null) ;
		jtc.getCaret().setDot(i + 1);
		return true;
	}

	@Override
	public boolean charBackspaced(Document doc, int i, JTextComponent jtc, char ch)
			throws BadLocationException {
		if(!isCompletable(ch)) {
			return false;
		}

		TokenSequence<LatteTokenId> ts = getLatteSequence(doc, i);

		if(ts == null) {
			return false;
		}

		if(ts.token().id() == LatteTokenId.STRING) {
			if(doc.getText(i-1, 1).charAt(0) == '\\') {
				return false;
			}
		}

		if(doc.getText(i, 1).charAt(0) == getMatching(ch)) {
			doc.remove(i, 1);
			return true;
		}
		return false;
	}

	@Override
	public boolean afterCharInserted(Document doc, int i, JTextComponent jtc, char ch)
			throws BadLocationException {
		return false;
	}

	@Override
	public int beforeBreak(Document doc, int i, JTextComponent jtc)
			throws BadLocationException {
		return -1;
	}

	@Override
	public OffsetRange findMatching(Document doc, int i) {
		return OffsetRange.NONE;
	}

	@Override
	public List<OffsetRange> findLogicalRanges(ParserResult pr, int i) {
		return new ArrayList<OffsetRange>();
	}

	@Override
	public int getNextWordOffset(Document doc, int i, boolean bln) {
		return -1;
	}

	private boolean isCompletable(char ch) {
		switch(ch) {
			//case '{': return '}';
			case '(':
			case '[':
			case '"':
			case '\'':
				return true;

			default:
				return false;
		}
	}

	private char getMatching(char ch) {
		switch(ch) {
			//case '{': return '}';
			case '(': return ')';
			case '[': return ']';
			case '"': return '"';
			case '\'': return '\'';

			default:
				return ch;
		}
	}

	private TokenSequence<LatteTokenId> getLatteSequence(Document doc, int pos)
			throws BadLocationException {
        TokenSequence<LatteTopTokenId> ts = LexUtils.getTopSequence(doc);

		ts.move(pos);

		if(!ts.moveNext() && !ts.movePrevious()) {
			return null;
		}

		if(ts.token().id() == LatteTopTokenId.LATTE) {
			TokenSequence<LatteTokenId> ts2 = LexUtils.getSequence(ts.token());

			ts2.move(pos - ts.offset());

			if(!ts2.moveNext() && !ts2.movePrevious()) {
				return null;
			}

			return ts2;
		}

		return null;
	}

}
