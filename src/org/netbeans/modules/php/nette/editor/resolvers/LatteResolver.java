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

package org.netbeans.modules.php.nette.editor.resolvers;

import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.php.nette.editor.Embedder;
import org.netbeans.modules.php.nette.editor.macros.processors.MacroProcessorFactory;
import org.netbeans.modules.php.nette.editor.macros.processors.MacroProcessor;
import org.netbeans.modules.php.nette.lexer.LatteTokenId;
import org.netbeans.modules.php.nette.lexer.LatteTopTokenId;
import org.netbeans.modules.php.nette.macros.LatteMacro;
import org.netbeans.modules.php.nette.macros.MacroDefinitions;
import org.netbeans.modules.php.nette.utils.LexUtils;

/**
 *
 * @author Ondřej Brejla
 */
public class LatteResolver extends TemplateResolver {

	public LatteResolver(Embedder embedder) {
		super(embedder);
	}

	@Override
	public void solve(Token t, TokenSequence<LatteTopTokenId> sequence) {
		String props = (String) t.getProperty("macro");			// get n:attr macro name if it exists

		if (props != null) {
			setMacroName(props);								// store macro name in macroName
		} else if (t.text().charAt(0) == '{') {
			setMacroName(null);									// else it is a macro starting with {
		}
		
		TokenSequence<LatteTokenId> sequence2 = LexUtils.getSequence(t);

		boolean endMacro = false;
		String macro = (getMacroName() != null ? getMacroName() : "");					// macro name used internally

		sequence2.moveStart();
		while(sequence2.moveNext()) {
			Token<LatteTokenId> t2 = sequence2.token();
			
			if (isEndMacro(t2, sequence2)) {		// is end macro {/
				endMacro = true;
			}
			
			if (t2.id() == LatteTokenId.MACRO) {									// store macro name
				macro = t2.text().toString();
				setMacroName(null);
				continue;
			}
			
			if (t2.id() == LatteTokenId.VARIABLE && macro.equals("")) {
				embedder.embed("@@@");
				return;
			}

			int start = sequence2.offset() + sequence.offset();
			if (!macro.equals("")) {
				MacroProcessor macroProcessor = MacroProcessorFactory.getMacroProcessor(macro);
				macroProcessor.process(sequence, sequence2, start, macro, endMacro, embedder);
				LatteMacro m = MacroDefinitions.getMacro(macro);
				if(m != null && !m.isControl()) {
					embedder.embed("@@@");
				}
				break;
			}
		}
	}

	private boolean isEndMacro(Token<LatteTokenId> t2, TokenSequence<LatteTokenId> sequence2) {
		return (t2.id() == LatteTokenId.END_SLASH && sequence2.offset() <= 2);
	}

}
