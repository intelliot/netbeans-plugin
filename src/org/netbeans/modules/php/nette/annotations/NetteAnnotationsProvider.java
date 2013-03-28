/*
 * The MIT license
 *
 * Copyright (c) 2013 Ondřej Brejla <ondrej@brejla.cz>
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
package org.netbeans.modules.php.nette.annotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.netbeans.modules.php.spi.annotation.AnnotationCompletionTag;
import org.netbeans.modules.php.spi.annotation.AnnotationCompletionTagProvider;
import org.openide.util.NbBundle;

/**
 *
 * @author Ondrej Brejla <obrejla@netbeans.org>
 */
public class NetteAnnotationsProvider extends AnnotationCompletionTagProvider {

	@NbBundle.Messages({
		"NetteAnnotationsName=Nette",
		"NetteAnnotationsDescription=Annotations for Nette Framework"
	})
	public NetteAnnotationsProvider() {
		super("Nette Annotations", Bundle.NetteAnnotationsName(), Bundle.NetteAnnotationsDescription()); //NOI18N
	}

	@Override
	public List<AnnotationCompletionTag> getFunctionAnnotations() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<AnnotationCompletionTag> getTypeAnnotations() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<AnnotationCompletionTag> getFieldAnnotations() {
		List<AnnotationCompletionTag> result = new ArrayList<AnnotationCompletionTag>();
		result.add(new AnnotationCompletionTag("persistent", "@persistent"));
		return result;
	}

	@Override
	public List<AnnotationCompletionTag> getMethodAnnotations() {
		return Collections.EMPTY_LIST;
	}

}
