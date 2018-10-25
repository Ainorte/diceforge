- FacetType<enum>
- ResourceType<enum>

- Operation

- Facet<FacetType, count>
- Player<ResourceInventory, MovementCount, CardInventory, FacetInventory, Dice<[6 facet]>[2]>
- TempPlayer<Player, Facets, Operations>

- Rule
- FacetRule<Rule>
- CardRule<Rule>
- GameRule<Rule>
- RuleSet<FacetRule[], CardRule[], GameRule[]>
- RuleFactory -> resolveRuleSet(playerCount) => RuleSet

- FacetRuleManager -> resolve(Facet[]) => Operation[]

- Game<Player[], RuleSet, FacetRuleManager>
