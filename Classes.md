- [x] FacetType<enum>
- [x] ResourceType<enum>
- [x] CardType<enum>

- [x] Operation

- [X] Facet<FacetType, count>
- [X] Player<ResourceInventory, MovementCount, CardInventory, FacetInventory, Dice<[6 facet]>[2]>
- [X] TempPlayer<Player, Facets, Operations>

- [x] Rule
- [x] FacetRule<Rule>
- [x] CardRule<Rule>
- [x] GameRule<Rule>
- [ ] RuleSet<FacetRule[], CardRule[], GameRule[]>
- [ ] RuleFactory -> resolveRuleSet(playerCount) => RuleSet

- [ ] FacetRuleManager -> resolve(Facet[]) => Operation[]

- [X] Game<Player[], RuleSet, FacetRuleManager>
