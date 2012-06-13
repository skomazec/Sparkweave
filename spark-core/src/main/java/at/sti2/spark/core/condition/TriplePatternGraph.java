/*
 * Copyright (c) 2010, University of Innsbruck, Austria.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * You should have received a copy of the GNU Lesser General Public License along
 * with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package at.sti2.spark.core.condition;

import java.util.ArrayList;
import java.util.List;

import at.sti2.spark.core.invoker.HandlerProperties;
import at.sti2.spark.core.prefix.Prefix;

/**
 * 
 * Triple pattern represents an ordered list of triple conditions
 * 
 * @author srdkom
 */
public class TriplePatternGraph {

	private List <TripleCondition> selectConditions = null;
	private List <TripleCondition> constructConditions = null;
	private List <Prefix> prefixes = null;
	private List <HandlerProperties> handlers = null;

	//The timewindow unit is ms
	private long timeWindowLength = 0l;
	
	public TriplePatternGraph() {
		super();
		selectConditions = new ArrayList <TripleCondition> ();
		constructConditions = new ArrayList <TripleCondition> ();
		prefixes = new ArrayList <Prefix> ();
		handlers = new ArrayList <HandlerProperties>();
	}

	public List<TripleCondition> getSelectConditions() {
		return selectConditions;
	}
	
	public List<TripleCondition> getConstructConditions() {
		return constructConditions;
	}
	
	public List<Prefix> getPrefixes() {
		return prefixes;
	}

	public void addSelectTripleCondition(TripleCondition condition) {
		selectConditions.add(condition);
	}
	
	public void addConstructTripleCondition(TripleCondition condition) {
		constructConditions.add(condition);
	}
	
	public void addPrefix(Prefix prefix){
		prefixes.add(prefix);
	}
	
	public TripleCondition getSelectConditionByIndex(int index){
		return selectConditions.get(index);
	}
	
	public TripleCondition getConstructConditionByIndex(int index){
		return constructConditions.get(index);
	}
	
	public Prefix getPrefixByIndex(int index){
		return prefixes.get(index);
	}
	
	public long getTimeWindowLength(){
		return timeWindowLength;
	}
	
	public void setTimeWindowLength(long timeWindowLength){
		this.timeWindowLength = timeWindowLength;
	}
	
	public List<HandlerProperties> getHandlers() {
		return handlers;
	}

	public void addHandlerProperties(HandlerProperties handlerProperties) {
		this.handlers.add(handlerProperties);
	}

	public String getNamespaceByLabel(String label){
		String namespace = null;
		
		for (Prefix prefix : prefixes)
			if (prefix.getLabel().equals(label)){
				namespace = prefix.getNamespace();
				break;
			}
		
		return namespace;
	}
	
	public String toString(){
		
		StringBuffer buffer = new StringBuffer();
		
		if (prefixes.size() > 0){
			buffer.append("PREFIXES\n");
			for (Prefix prefix : prefixes){
				buffer.append(prefix.getLabel());
				buffer.append(" : ");
				buffer.append(prefix.getNamespace());
				buffer.append('\n');
			}
		}
		
		if (handlers != null){
			buffer.append("HANDLERS\n");
			buffer.append(handlers);
		}
		
		buffer.append("CONSTRUCT\n");
		
		for (TripleCondition condition : constructConditions)
			for (TripleConstantTest constantTest : condition.getConstantTests()){
				buffer.append(constantTest.getTestField());
				buffer.append(' ');
				buffer.append(constantTest.getLexicalTestSymbol());
				buffer.append('\n');
			}
		
		buffer.append("SELECT\n");
		
		for (TripleCondition condition : selectConditions)
			for (TripleConstantTest constantTest : condition.getConstantTests()){
				buffer.append(constantTest.getTestField());
				buffer.append(' ');
				buffer.append(constantTest.getLexicalTestSymbol());
				buffer.append('\n');
			}
		
		return buffer.toString();
	}

	public void setPrefixes(List<Prefix> prefixes) {
		this.prefixes = prefixes;
	}
}
