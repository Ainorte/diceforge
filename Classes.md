- [x] FacetType<enum>
- [x] ResourceType<enum>

- [x] Operation

- [ ] Facet<FacetType, count>
- [ ] Player<ResourceInventory, MovementCount, CardInventory, FacetInventory, Dice<[6 facet]>[2]>
- [ ] TempPlayer<Player, Facets, Operations>

- [x] Rule
- [x] FacetRule<Rule>
- [x] CardRule<Rule>
- [x] GameRule<Rule>
- [ ] RuleSet<FacetRule[], CardRule[], GameRule[]>
- [ ] RuleFactory -> resolveRuleSet(playerCount) => RuleSet

- [ ] FacetRuleManager -> resolve(Facet[]) => Operation[]

- [ ] Game<Player[], RuleSet, FacetRuleManager>
