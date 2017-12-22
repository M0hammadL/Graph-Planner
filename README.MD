


Graph-planner
===============

This is a java implementation of famous "graph plan" algorithm. Graphplan is a general-purpose planner
for STRIPS-style domains, based on ideas used in graph algorithms. Given a problem statement, Graphplan
explicitly constructs and annotates a compact structure called a Planning Graph, in which a plan is a kind of
"flow" of truth-values through the graph.This graph has the property that useful information for constraining search 
can quickly be propagated through the graph as it is being built. Graphplan then exploits this information in the search
for a plan. Graphplan was created by Avrim Blum and Merrick Furst, with subsequent extensions and improvements made by many
researchers at many different institutions around the world.

Input format:
============
problem setting should be given as input : 
  
 
OBJECTS:3

a


b


c




INITIAL-STATE:4



CLEAR



a


ARM-EMPTY



ON


a


b


ON-TABLE


b






GOALS:2


ON-TABLE


a


CLEAR


b

