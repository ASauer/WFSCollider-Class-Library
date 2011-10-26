/*
    GameOfLife WFSCollider - Wave Field Synthesis spatialization for SuperCollider.
    The Game Of Life Foundation. http://gameoflife.nl
    Copyright 2006-2011 Miguel Negrao.

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

WFSMenuBar {

    *new { |index = 3|
		
		var wfsMenu, scoreMenu, pathMenu, helpMenu, viewMenu, defaultMenu, addEvent, events, menus, sessionMenu;

		menus = ();
/* USession */
		sessionMenu = SCMenuGroup.new(nil, "Session", index);
		SCMenuItem.new(sessionMenu,  "New").action_({
			USession.new.gui
		}).setShortCut("n",true);
		
		SCMenuItem.new(sessionMenu, "Open").action_({
			USession.read(nil, USessionGUI(_) )
		});
		
		SCMenuItem.new(sessionMenu, "Save").action_({
			USession.current !! _.save
		})
		.setShortCut("s",true);			
			
		SCMenuItem.new(sessionMenu, "Save as").action_({
			USession.current !! _.saveAs
		})
		.setShortCut("S",true);
		SCMenuSeparator.new(sessionMenu);

		SCMenuItem.new(sessionMenu, "add UChain").action_({
			USession.current !! _.add(UChain(\sine,\output))
		});

		//events

/* EVENTS */
		events = SCMenuGroup.new(nil, "Scores", index + 1);

/* USCORE */
		menus[\uscore] = SCMenuGroup.new(events, "File");
		SCMenuItem.new(menus[\uscore],  "New").action_({
			UScore.new.gui;
		});

		SCMenuItem.new(menus[\uscore], "Open").action_({
			UScore.openWFS(nil, UScoreEditorGUI(_) )
		});

		SCMenuItem.new(menus[\uscore], "Save").action_({
			UScore.current !! _.save
		});

		SCMenuItem.new(menus[\uscore], "Save as").action_({
			UScore.current !! _.saveAs
		});

		SCMenuItem.new(events, "Add Score to current session").action_({
			USession.current !! { |x| UScore.current !! { |y| x.add(y) } }
		}).setShortCut("A",true);

		SCMenuSeparator.new(events);

		SCMenuItem.new(events, "Add Event").action_({
			UScoreEditorGUI.current !! { |x| x.editor.addEvent }
		}).setShortCut("A",true);

		SCMenuItem.new(events, "Edit").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.editSelected }
		}).setShortCut("i",true);

		SCMenuItem.new(events, "Delete").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.deleteSelected }
		}).setShortCut("r",true);

		SCMenuSeparator.new(events);

	    SCMenuItem.new(events, "Copy").action_({
	        UScoreEditorGUI.currentSelectedEvents !! UScoreEditor.copy(_)
		}).setShortCut("C",true);

		SCMenuItem.new(events, "Paste").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.currentEditor.pasteAtCurrentPos }
		}).setShortCut("P",true);
		
		SCMenuSeparator.new(events);
				
		SCMenuItem.new(events, "Select All").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.selectAll }

		}).setShortCut("a",true);	
		
		SCMenuItem.new(events, "Select Similar").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.selectSimilar }
		});		
		
		//sort
		SCMenuSeparator.new(events);
		
		SCMenuItem.new(events, "Overlapping events to new tracks").action_({
			UScoreEditorGUI.current !! { |x| x.score.cleanOverlaps }
		});
		
		//mute, solo
		SCMenuSeparator.new(events);
		
		SCMenuItem.new(events, "Mute selected").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.muteSelected }
		}).setShortCut("m",true);
		
		SCMenuItem.new(events, "Unmute selected").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.unmuteSelected }
		}).setShortCut("u",true);
		
		SCMenuItem.new(events, "Unmute all").action_({
			UScoreEditorGUI.current !! { |x| x.editor.unmuteAll }
		});
		
		SCMenuItem.new(events, "Solo selected").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.soloSelected }
		}).setShortCut("p",true);

		//tracks
		SCMenuSeparator.new(events);
		
		SCMenuItem.new(events, "Add Track").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.addTrack }
		});
		
		SCMenuItem.new(events, "Remove Unused Tracks").action_({
			UScoreEditorGUI.current !! { |x| x.scoreView.removeUnusedTracks }
		});
		
		/*//paths
		pathMenu = SCMenuGroup.new(nil, "Paths", index + 2);
		
		SCMenuItem.new(pathMenu, "Generate New").action_(  
			{ WFSPathEditor.newEditor( 
				[ WFSPath( [ [0,0],[1,0] ], name: \temp ) ], "WFSPathEditor", true ); 
			});
		SCMenuItem.new(pathMenu, "Draw New").action_({  WFSDrawWindow.new; });
		
							
		defaultMenu = SCMenuGroup.new(pathMenu, "create default WFSPath");
		SCMenuItem.new(defaultMenu, "Circle").action_( {WFSPath.circle.edit; });
		SCMenuItem.new(defaultMenu, "Rand").action_( {WFSPath.rand.edit; });
		SCMenuItem.new(defaultMenu, "Spiral").action_( {WFSPath.spiral.edit; });
		SCMenuItem.new(defaultMenu, "Lissajous").action_( {WFSPath.lissajous.edit; });
		SCMenuItem.new(defaultMenu, "Line").action_( {WFSPath.line.edit; });
		SCMenuItem.new(defaultMenu, "Rect").action_( {WFSPath.rect.edit; });
		
		SCMenuSeparator.new(pathMenu);
		
		SCMenuItem.new(pathMenu, "Open file").action_({   
								CocoaDialog.getPaths(
									{arg paths;
										//WFSPathEditor.close;
										WFSPathEditor( WFSPathArray.readWFSFile(paths.first) );
										}, { "cancelled".postln; });
								});
							
		SCMenuItem.new(pathMenu, "Import SVG file").action_({   
								CocoaDialog.getPaths(
									{arg paths;
										var file;
										file = SVGFile.read( paths[0] );
										if( file.hasCurves )
											{ SCAlert( "file '%'\nincludes curved segments. How to import?"
												.format( file.path.basename ),
												[ "cancel", "lines only", "curves" ],
												[ {},{  WFSPathEditor( WFSPathArray
														.fromSVGFile( file, useCurves: false) ) },
												  {  WFSPathEditor( WFSPathArray
												  		.fromSVGFile( file, useCurves: true) ) } ]  
												); }
											{ WFSPathEditor( WFSPathArray.fromSVGFile( file ) ) }
										//WFSPathEditor.close;
										}, { "cancelled".postln; });
								});
        */
		//view
		viewMenu = SCMenuGroup.new(nil, "View", index + 3);
		SCMenuItem.new(viewMenu, "All").action_( {WFSEQ.new; WFSTransport.new; WFSLevelBus.makeWindow;}).setShortCut("T",true);
		SCMenuSeparator.new(viewMenu);
		SCMenuItem.new(viewMenu, "EQ").action_( {WFSEQ.new; });
		SCMenuItem.new(viewMenu, "Transport").action_( {WFSTransport.new; });		SCMenuItem.new(viewMenu, "Level").action_( {WFSLevelBus.makeWindow; });
		if(WFSServers.default.isSingle){
			SCMenuItem.new(viewMenu, "Meter").action_({
				ServerMeter(WFSServers.default.masterServer,0,2);
			});
		};

	}
}

