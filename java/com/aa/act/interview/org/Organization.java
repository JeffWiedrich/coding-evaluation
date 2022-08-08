package com.aa.act.interview.org;

import java.util.Optional;
import java.util.Random; 

public abstract class Organization {

	private Position root;
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		//your code here
		if (person == null || title == null) return Optional.empty();
		
		Optional<Position> searchedPosition = positionSearch(root, person, title);
	}
	
	private Optional<Position> positionSearch (Position recursivePosition,Name person, String title) {
		// Hire person if position is available
	    if (recursivePosition.getTitle().equals(title)) return hirePerson(person, recursivePosition);
		// Return empty if no position has that title
	    if (recursivePosition.getDirectReports.isEmpty()) return Optional.empty();
	    
		// Drill down the position tree to find if the position exists
	    for (Position currentPosition : recursivePosition.getDirectReports()) {
	        Optional<Position> returnValue = positionSearch(currentPosition, person, title);
	        if (returnValue.isPresent()) return returnValue; // Return results up the recursive hirearchy
	    }
	}
	
	private Optional<Position> hirePerson (Name person, Position position) {
	    Random random = new Random(); // Generate employee ID
		// Hire person
	    position.setEmployee(Optional.of(new Employee(random.nextInt(256), person)));
	    return Optional.of(position); 
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}