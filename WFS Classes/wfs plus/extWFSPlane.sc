/*
    GameOfLife WFSCollider - Wave Field Synthesis spatialization for SuperCollider.
    The Game Of Life Foundation. http://gameoflife.nl
    Copyright 2006-2010 Wouter Snoei.

    GameOfLife WFSCollider software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    GameOfLife WFSCollider is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with GameOfLife WFSCollider.  If not, see <http://www.gnu.org/licenses/>.
*/

+ WFSPlane {

	width { ^WFSTools.fromRadians( radianWidth ) }
	
	width_ { |newWidth|
		radianWidth = WFSTools.asRadians( newWidth );
		}
		
	offset { ^WFSTools.fromRadians( radianOffset ) }
	
	offset_ { |newOffset|
		radianOffset = WFSTools.asRadians( newOffset );
		}
		
	
		
	}